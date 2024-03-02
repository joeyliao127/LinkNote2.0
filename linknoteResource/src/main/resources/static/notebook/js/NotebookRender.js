class NotebookRender {
  #role;
  #filter = {
    offset: 0,
    limit: 20,
    star: false,
    keyword: null,
    tag: null,
    sort: false,
  };

  constructor() {}

  async renderMyNotebooks() {
    this.#role = "owner";
    await this.renderNotebookArea("myNotebooks");
  }

  async renderCoNotebooks() {
    this.#role = "collaborator";
    await this.renderNotebookArea("coNotebooks");
  }

  async renderNotebook(notebook) {
    const notebookArea = document.querySelector(".notebookArea");
    notebookArea.innerHTML = "";
    const noteCtn = await this.genNoteCtn(notebook, "myNotebook");
    notebookArea.appendChild(noteCtn);
  }

  async renderCoNotebook(notebook) {
    const notebookArea = document.querySelector(".notebookArea");
    notebookArea.innerHTML = "";
    const noteCtn = await this.genNoteCtn(notebook, "coNotebook");
    notebookArea.appendChild(noteCtn);
  }

  async renderNotebookArea(renderPage) {
    let notebookArea = document.querySelector(".notebookArea");
    if (notebookArea) {
      notebookArea.remove();
    }
    notebookArea = await this.genNotebookArea(renderPage);
    ReRenderElement.reRenderMain(notebookArea);
  }

  async genNotebookArea(renderPage) {
    const notebookArea = document.createElement("section");
    notebookArea.classList.add("notebookArea");
    notebookArea.classList.add("scroll");
    notebookArea.innerHTML = `
    <div class="notebooksTop">
      <h3></h3>
      <div class="search">
        <input
          type="text"
          id="searchNotebook"
          placeholder="search notebook"
        />
        <img
          src="https://cdn.linknote.online/linknote-icons/search.png"
          alt="search"
        />
      </div>
    </div>
    `;

    let path = "";
    const searchElemet = notebookArea.querySelector("#searchNotebook");
    if (this.#role === "owner") {
      path = `/api/notebooks?offset=${this.#filter.offset}&limit=${
        this.#filter.limit
      }`;
      notebookArea.querySelector("h3").textContent = "My Notebook";

      searchElemet.addEventListener("keypress", (e) => {
        const keyword = searchElemet.value;
        if (e.key === "Enter" && keyword) {
          this.renderNotebookCtn(path + `&keyword=${keyword}`);
          return;
        }
      });
    } else {
      path = `/api/coNotebooks?offset=${this.#filter.offset}&limit=${
        this.#filter.limit
      }`;
      searchElemet.addEventListener("keypress", (e) => {
        const keyword = searchElemet.value;
        if (e.key === "Enter" && keyword) {
          this.renderNotebookCtn(path + `&keyword=${keyword}`);
          return;
        }
      });
      notebookArea.querySelector("h3").textContent = "Collaborative Notebook";
    }
    notebookArea.appendChild(await this.genNotebookCtn(path, renderPage));
    return notebookArea;
  }

  async renderNotebookCtn(path, renderPage) {
    const notebookCtn = document.querySelector(".notebookCtn");
    if (notebookCtn) {
      notebookCtn.remove();
    }
    const newNotebookCtn = await this.genNotebookCtn(path, renderPage);
    newNotebookCtn.classList.add("notebookCtn");
    document.querySelector(".notebookArea").appendChild(newNotebookCtn);
  }

  async genNotebookCtn(path, renderPage) {
    const notebookCtn = document.createElement("div");
    notebookCtn.classList.add("notebookCtn");
    const response = await FetchDataHandler.fetchData(path, "GET");
    let notebooks = await response.json();
    notebooks = notebooks.notebooks;
    if (notebooks.length === 0) {
      if (this.#role === "owner") {
        return this.genCreateNotebookBlock();
      } else {
        return this.genNoCoNotebookBlock();
      }
    }

    notebooks.forEach(async (notebook) => {
      const divide = document.createElement("div");
      divide.classList.add("divide");
      const noteCtn = await this.genNoteCtn(notebook, renderPage);
      notebookCtn.appendChild(divide);
      notebookCtn.appendChild(noteCtn);
    });

    return notebookCtn;
  }

  renderNoteCtn(notebook) {
    const noteCtn = document.querySelector(".noteCtn");
    if (noteCtn) {
      noteCtn.remove();
    }
    let newNoteCtn = document.createElement("section");
    newNoteCtn.classList.add("noteCtn");
    newNoteCtn.appendChild(this.genNoteCtn(notebook));
    document.querySelector(".notebookCtn").appendChild(newNoteCtn);
  }

  async genNoteCtn(notebookInfo, renderPage) {
    const noteCtn = document.createElement("section");
    noteCtn.classList.add("noteCtn");
    const divideLine = document.createElement("div");
    divideLine.classList.add("divdeLine");
    let notebookBtnCtn;

    if (renderPage === "myNotebooks") {
      notebookBtnCtn = genMyNotebooksToolBar(notebookInfo.id);
      noteCtn.appendChild(notebookBtnCtn);
    } else if (renderPage === "myNotebook") {
      notebookBtnCtn = genNotebookToolBar(notebookInfo.id);
      noteCtn.appendChild(notebookBtnCtn);
    } else if (renderPage === "coNotebooks") {
    } else if (renderPage === "coNotebook") {
      notebookBtnCtn = genCoNotebookToolBar(notebookInfo.id);
      noteCtn.appendChild(notebookBtnCtn);
    }

    noteCtn.appendChild(genNotebookNameElement(notebookInfo.name));
    noteCtn.appendChild(
      genNotebookDescriptionElement(notebookInfo.description, this.#role)
    );

    noteCtn.appendChild(genUpdateBtnCtn(notebookInfo));
    noteCtn.appendChild(
      await this.genNoteCardCtn(notebookInfo.id, renderPage, this.#filter)
    );
    return noteCtn;

    function genNotebookDescriptionElement(description, role) {
      const descriptionElement = document.createElement("p");
      descriptionElement.classList.add("description");
      descriptionElement.textContent = description;
      if (role === "owner") {
        descriptionElement.addEventListener("click", editMode);
        descriptionElement.setAttribute("contenteditable", true);
      }
      return descriptionElement;
    }

    function genNotebookNameElement(name) {
      const nameElement = document.createElement("h4");
      nameElement.textContent = name;
      return nameElement;
    }

    function genMyNotebooksToolBar(notebookId) {
      const toolBarCtn = document.createElement("div");
      toolBarCtn.classList.add("toolBar");
      toolBarCtn.appendChild(genDeleteNotebookBtn(notebookId));
      return toolBarCtn;
    }

    function genNotebookToolBar(notebookId) {
      const toolBarCtn = document.createElement("div");
      toolBarCtn.classList.add("toolBar");
      toolBarCtn.appendChild(genCreateNoteBtn(notebookId));
      toolBarCtn.appendChild(genAddCollabortorBtn(notebookId));
      toolBarCtn.appendChild(genAllNoteBtn(notebookId));
      toolBarCtn.appendChild(genTagBtn(notebookId));
      toolBarCtn.appendChild(genSortByTime(notebookId));
      toolBarCtn.appendChild(genStar(notebookId));
      toolBarCtn.appendChild(genSearchNoteInput(notebookId));
      toolBarCtn.appendChild(genDeleteNotebookBtn(notebookId));
      return toolBarCtn;
    }
    function genCoNotebookToolBar(notebookId) {
      const toolBarCtn = document.createElement("div");
      toolBarCtn.classList.add("toolBar");
      toolBarCtn.appendChild(genCreateNoteBtn(notebookId));
      toolBarCtn.appendChild(genAddCollabortorBtn(notebookId));
      toolBarCtn.appendChild(genAllNoteBtn(notebookId));
      toolBarCtn.appendChild(genTagBtn(notebookId));
      toolBarCtn.appendChild(genSortByTime(notebookId));
      toolBarCtn.appendChild(genStar(notebookId));
      toolBarCtn.appendChild(genSearchNoteInput(notebookId));
      return toolBarCtn;
    }

    function genCreateNoteBtn(notebookId) {
      const createNoteBtn = document.createElement("div");
      createNoteBtn.classList.add("createNoteBtn");
      createNoteBtn.innerHTML = "<p>New note</p>";
      createNoteBtn.addEventListener("click", () => {
        createNote(notebookId);
      });
      return createNoteBtn;

      async function createNote(notebookId) {
        const path = `/api/notebooks/${notebookId}/notes`;
        const response = await FetchDataHandler.fetchData(path, "POST", {
          notebookId,
        });
        if (response.ok) {
          const data = await response.json();
          window.location.href = `/notebooks/${notebookId}/notes/${data.noteId}`;
        }
      }
    }

    function genAddCollabortorBtn() {
      const collaboratorBtn = document.createElement("div");
      collaboratorBtn.classList.add("toolBtn");
      collaboratorBtn.id = "collaboratorBtn";
      collaboratorBtn.innerHTML = ` 
      <img
      src="https://cdn.linknote.online/linknote-icons/addCollaborator.png"
      alt="collaboratorBtn"
    />`;
      return collaboratorBtn;
    }

    function genAllNoteBtn() {
      const allNoteBtn = document.createElement("div");
      allNoteBtn.classList.add("toolBtn");
      allNoteBtn.id = "allNoteBtn";
      allNoteBtn.innerHTML = `
      <img
          src="https://cdn.linknote.online/linknote-icons/box.png"
          alt="box"
        />`;
      return allNoteBtn;
    }

    function genTagBtn() {
      const tagBtn = document.createElement("div");
      tagBtn.classList.add("toolBtn");
      tagBtn.id = "tagBtn";
      tagBtn.innerHTML = `
      <img
          src="https://cdn.linknote.online/linknote-icons/tag.png"
          alt="tag"
        />`;
      return tagBtn;
    }

    function genSortByTime() {
      const sortByTimeBtn = document.createElement("div");
      sortByTimeBtn.classList.add("toolBtn");
      sortByTimeBtn.id = "sortByTimeBtn";
      sortByTimeBtn.innerHTML = `
      <img
          src="https://cdn.linknote.online/linknote-icons/clock.png"
          alt="clock"
        />
      `;
      return sortByTimeBtn;
    }

    function genStar() {
      const starBtn = document.createElement("div");
      starBtn.classList.add("toolBtn");
      starBtn.id = "starBtn";
      starBtn.innerHTML = `
      <img
      src="https://cdn.linknote.online/linknote-icons/full-star.png"
      alt="star"
    />
      `;
      return starBtn;
    }

    function genSearchNoteInput() {
      const searchInput = document.createElement("div");
      searchInput.innerHTML = `
      <input
        type="text"
        id="searchNote"
        placeholder="search notes"
      />
      <img
        src="https://cdn.linknote.online/linknote-icons/search.png"
        alt="search"
      />`;
      searchInput.classList.add("searchNote");
      searchInput.classList.add("search");
      searchInput.querySelector("input").addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
        }
      });
      searchInput.querySelector("img").addEventListener("click", () => {});
      return searchInput;
    }

    function genDeleteNotebookBtn(notebookId) {
      const deleteNotebookBtn = document.createElement("div");
      deleteNotebookBtn.classList.add("toolBtn");
      deleteNotebookBtn.innerHTML = `
      <img
        src="https://cdn.linknote.online/linknote-icons/trash-white.png"
        alt="deleteBtn"
      />`;
      deleteNotebookBtn.addEventListener("click", deleteNotebook);

      return deleteNotebookBtn;
      function deleteNotebook() {
        const path = `/api/notebooks/${notebookId}`;
        DeleteAlert.renderDeleteAletBox(
          noteCtn,
          "notebook",
          notebookInfo.name,
          path
        );
      }
    }

    function genUpdateBtnCtn(notebook) {
      const updateBtnCtn = document.createElement("div");
      updateBtnCtn.classList.add("updateBtnCtn");
      updateBtnCtn.appendChild(genUpdateBtn(notebook));
      updateBtnCtn.appendChild(
        genCancelUpdateBtn(notebook.description, notebook.name)
      );
      return updateBtnCtn;
    }

    function genUpdateBtn(notebook) {
      const updateBtn = document.createElement("div");
      updateBtn.classList.add("updateBtn");
      updateBtn.classList.add("display-none");
      updateBtn.addEventListener("click", () => {
        updateNotebook(notebook.id);
      });
      updateBtn.textContent = "Save";
      return updateBtn;

      async function updateNotebook(notebookId) {
        const path = `/api/notebooks/${notebookId}`;
        const name = noteCtn.querySelector("h4").textContent;
        const description = noteCtn.querySelector(".description").textContent;
        const requestBody = {
          name,
          description,
        };

        const response = await FetchDataHandler.fetchData(
          path,
          "PUT",
          requestBody
        );

        if (response.ok) {
          MessageMaker.success("update success!");
          notebook.description = description;
          notebook.name = name;
          cancelEditMode();
        }
      }
    }

    function genCancelUpdateBtn(oldDescription, oldName) {
      const cancelUpdateBtn = document.createElement("div");
      cancelUpdateBtn.classList.add("display-none");
      cancelUpdateBtn.classList.add("cancelUpdateBtn");
      cancelUpdateBtn.textContent = "Cancel";
      cancelUpdateBtn.addEventListener("click", () => {
        cancelEditMode(oldDescription, oldName);
      });
      return cancelUpdateBtn;
    }

    function editMode() {
      const description = noteCtn.querySelector(".description");
      description.classList.add("editMode");
      noteCtn.querySelector(".updateBtn").classList.remove("display-none");
      noteCtn
        .querySelector(".cancelUpdateBtn")
        .classList.remove("display-none");
    }

    function cancelEditMode() {
      const description = noteCtn.querySelector(".description");
      description.classList.remove("editMode");
      noteCtn.querySelector(".updateBtn").classList.add("display-none");
      noteCtn.querySelector(".cancelUpdateBtn").classList.add("display-none");
    }
  }

  renderNoteCardCtn(notebookId, renderPage, filters) {
    const noteCtn = document.querySelector(".noteCtn");
    document.querySelector(".noteCardCtn").remove();
    const noteCardCtn = this.genNoteCardCtn(notebookId, renderPage, filters);
    noteCtn.appendChild(noteCardCtn);
  }

  async genNoteCardCtn(notebookId, renderPage, filters) {
    const noteCardCtn = document.createElement("div");
    noteCardCtn.classList.add("noteCardCtn");
    if (renderPage === "myNotebook" || renderPage === "coNotebook") {
      noteCardCtn.classList.add("notebookPage");
    } else {
      noteCardCtn.classList.add("notebooksPage");
    }
    const path = `/api/notebooks/${notebookId}/notes?offset=${0}&limit=${20}`;
    const response = await FetchDataHandler.fetchData(path, "GET");
    const notes = await response.json();
    console.log("notes:");
    console.log(notes);
    if (notes.length === 0) {
      return noteCardCtn;
    } else {
      notes.notes.forEach((note) => {
        noteCardCtn.appendChild(this.genNoteCard(note, notebookId));
      });
      return noteCardCtn;
    }
  }

  genNoteCard(note, notebookId) {
    const noteCard = document.createElement("a");
    noteCard.classList.add("noteCard");
    noteCard.href = `/notebooks/${notebookId}/notes/${note.noteId}`;
    noteCard.appendChild(
      genStar(note.name, note.star, notebookId, note.noteId)
    );
    noteCard.appendChild(genNoteName(note.name));
    noteCard.appendChild(genQuestion(note.question));
    noteCard.appendChild(genCreateTime(note.createDate));
    return noteCard;

    function genStar(name, stared, notebookId, noteId) {
      const star = document.createElement("div");
      star.classList.add("star");
      if (stared) {
        star.classList.add("star-full");
      } else {
        star.classList.add("star-empty");
      }
      star.addEventListener("click", updateStar);
      return star;

      function updateStar(e) {
        e.preventDefault();
        e.stopPropagation();
        const path = `/api/notebooks/${notebookId}/notes/${noteId}`;
        let requestBody = {};
        if (star.classList.contains("star-empty")) {
          requestBody = {
            name,
            star: true,
          };
          star.classList.add("star-full");
          star.classList.remove("star-empty");
        } else {
          requestBody = {
            name,
            star: false,
          };
          star.classList.add("star-empty");
          star.classList.remove("star-full");
        }
        FetchDataHandler.fetchData(path, "PUT", requestBody);
      }
    }

    function genNoteName(name) {
      const nameElement = document.createElement("h5");
      nameElement.textContent = name;
      return nameElement;
    }

    function genQuestion(question) {
      const questionElement = document.createElement("p");
      questionElement.textContent = question;
      questionElement.classList.add("question");
      return questionElement;
    }

    function genCreateTime(time) {
      time = time.split(" ")[0];
      const timeElement = document.createElement("p");
      timeElement.textContent = time;
      timeElement.classList.add("createTime");
      return timeElement;
    }
  }

  genCreateNotebookBlock() {
    const initNotebookWrapper = document.createElement("div");
    initNotebookWrapper.classList.add("initNotebookWrapper");
    initNotebookWrapper.innerHTML = `
    <div class="initNotebookWrapper">
      <div class="initNotebookCtn">
        <img
          src="https://cdn.linknote.online/linknote-icons/create-notebook.png"
          alt="create-notebook"
        />
        <div class="initCreateNotebookGroup">
          <h4>Create your first notebook!</h4>
          <div class="initBtns">
            <div id="initCreateNotebookBtn" class="no-select">
              Create notebook
            </div>
            <a href="" id="tutorials" class="no-select">Tutorials</a>
          </div>
        </div>
      </div>
    </div>
    `;

    initNotebookWrapper
      .querySelector("#initCreateNotebookBtn")
      .addEventListener("click", () => {
        CreateNotebookFormRender.renderCreateNotebookForm();
        CreateNotebookFormRender.main();
      });
    return initNotebookWrapper;
  }

  genNoCoNotebookBlock() {
    const noneElement = document.createElement("div");
    noneElement.classList.add("none");
    noneElement.textContent = "There is no collaborative notebooks.";
    return noneElement;
  }
}
