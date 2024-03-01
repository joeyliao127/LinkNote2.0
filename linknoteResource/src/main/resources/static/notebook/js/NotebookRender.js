class NotebookRender {
  #role;
  #myNotebookOffset = 0;
  #myNotebookLimit = 20;
  #coNotebookOffset = 0;
  #coNotebookLimit = 20;
  #searchNotebookOffset = 0;
  #searchNotebookLimit = 20;
  constructor() {}

  renderMyNotebooks() {
    this.#role = "owner";
    this.renderNotebookArea();
  }

  renderCoNotebooks() {
    this.#role = "collaborator";
    this.renderNotebookArea();
  }

  renderNotebookArea() {
    let notebookArea = document.querySelector(".notebookArea");
    if (notebookArea) {
      notebookArea.remove();
    }
    notebookArea = this.genNotebookArea();
    ReRenderElement.reRenderMain(notebookArea);
  }

  //還有search事件要處理
  genNotebookArea() {
    const notebookArea = document.createElement("section");
    notebookArea.classList.add("notebookArea");
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
      path = `/api/notebooks?offset=${this.#myNotebookOffset}&limit=${
        this.#myNotebookLimit
      }`;
      notebookArea.querySelector("h3").textContent = "My Notebook";

      searchElemet.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
          const keyword = searchElemet.value;
          this.renderNoteCtn(path + `&keyword=${keyword}`);
        }
      });
    } else {
      path = `/api/coNotebooks?offset=${this.#coNotebookOffset}&limit=${
        this.#coNotebookLimit
      }`;
      searchElemet.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
          this.renderNoteCtn(path + `&keyword=${keyword}`);
        }
      });
      notebookArea.querySelector("h3").textContent = "Collaborative Notebook";
    }
    notebookArea.appendChild(this.genNotebookCtn(path));
    return notebookArea;
  }

  renderNotebookCtn(path) {
    const notebookCtn = document.querySelector(".notebookCtn");
    if (notebookCtn) {
      notebookCtn.remove();
    }
    notebookCtn = this.genNotebookCtn(path);
    document.querySelector(".notebookAear").appendChild(notebookCtn);
  }

  async genNotebookCtn(path) {
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
      const noteCtn = await this.genNoteCtn(notebook);
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

  async genNoteCtn(notebook) {
    const noteCtn = document.createElement("section");
    noteCtn.classList.add("noteCtn");
    let delBtn;

    if (this.#role === "owner") {
      delBtn = genDeleteNotebookBtn(notebook.id);
      noteCtn.appendChild(delBtn);
    }
    genNotebookDescriptionElement.bind(this);
    genNotebookNameElement.bind(this);

    noteCtn.appendChild(genNotebookNameElement(notebook.name));
    noteCtn.appendChild(genNotebookDescriptionElement(notebook.description));
    noteCtn.appendChild(genUpdateBtnCtn(notebook.id));
    noteCtn.appendChild(await this.genNoteCardCtn(notebook.id));
    return noteCtn;

    function genNotebookDescriptionElement(description) {
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
      if (role === "owner") {
        this.renderNotebook(notebook.id);
      } else {
        NotebookRender.renderCoNotebook(notebook.id);
      }
      return nameElement;
    }

    function genDeleteNotebookBtn(notebookId) {
      const deleteNotebookBtn = document.createElement("img");
      deleteNotebookBtn.classList.add("deleteNotebookBtn");
      deleteNotebookBtn.src =
        "https://cdn.linknote.online/linknote-icons/trash-white.png";
      deleteNotebookBtn.addEventListener("click", deleteNotebook);

      return deleteNotebookBtn;
      function deleteNotebook() {
        const path = `/api/notebooks/${notebookId}`;
        DeleteAlert.renderDeleteAletBox(
          noteCtn,
          "notebook",
          notebook.name,
          path
        );
      }
    }

    function genUpdateBtnCtn(notebookId) {
      const updateBtnCtn = document.createElement("div");
      updateBtnCtn.classList.add("updateBtnCtn");
      updateBtnCtn.appendChild(genUpdateBtn(notebookId));
      updateBtnCtn.appendChild(genCancelUpdateBtn());
      return updateBtnCtn;
    }

    function genUpdateBtn(notebookId) {
      const updateBtn = document.createElement("div");
      updateBtn.classList.add("updateBtn");
      updateBtn.classList.add("display-none");
      updateBtn.addEventListener("click", updateNotebook);
      updateBtn.textContent = "Save";
      return updateBtn;

      async function updateNotebook() {
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

    function genCancelUpdateBtn() {
      const cancelUpdateBtn = document.createElement("div");
      cancelUpdateBtn.classList.add("display-none");
      cancelUpdateBtn.classList.add("cancelUpdateBtn");
      cancelUpdateBtn.textContent = "Cancel";
      cancelUpdateBtn.addEventListener("click", cancelEditMode);
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
      const name = noteCtn.querySelector("h4");
      description.classList.remove("editMode");
      noteCtn.querySelector(".updateBtn").classList.add("display-none");
      noteCtn.querySelector(".cancelUpdateBtn").classList.add("display-none");
      description.textContent = notebook.description;
      name.textContent = notebook.name;
    }
  }

  async genNoteCardCtn(notebookId) {
    const noteCardCtn = document.createElement("div");
    noteCardCtn.classList.add("noteCardCtn");
    const path = `/api/notebooks/${notebookId}/notes`;
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
