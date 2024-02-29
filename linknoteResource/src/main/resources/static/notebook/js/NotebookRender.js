class NotebookRender {
  constructor() {}

  static notebookTag = {};

  static renderNotebookMain = function (element) {
    const notebookArea = document.querySelector(".notebookArea");
    notebookArea.innerHTML = "";
    notebookArea.appendChild(element);
  };

  static renderMyNotebooks() {
    this.renderNotebooks("owner");
  }

  static renderCoNotebooks = function () {
    this.renderNotebooks("collaborator");
  };

  static renderNotebooks = async function (role) {
    const mainWrapper = document.createElement("div");
    mainWrapper.classList.add("mainWrapper");
    mainWrapper.innerHTML = `
    <section class="notebookArea">
    <div class="notebooksTop">
      <h3>My Notebooks</h3>
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
    <div class="notebookCtn">
    
    </div>
  </section>
    `;
    const notebookCtn = mainWrapper.querySelector(".notebookCtn");

    SideBarRender.notebooks.forEach(async (element) => {
      const noteCtn = await this.genNoteCtn(role, element);
      notebookCtn.appendChild(noteCtn);
    });

    MainRender.renderMain(mainWrapper);
  };

  static renderNotebook() {
    this.genNoteCtnWithFilter();
  }

  static genNoteCtn = async function (role, notebook) {
    const noteCtn = document.createElement("section");
    noteCtn.classList.add("noteCtn");
    let delBtn;

    if (role === "owner") {
      delBtn = genDeleteNotebookBtn(notebook.id);
    } else {
      delBtn = genLeaveCoNotebookBtn(notebook.id);
    }

    noteCtn.appendChild(delBtn);
    noteCtn.appendChild(genNotebookNameElement(notebook.name, role));
    noteCtn.appendChild(
      genNotebookDescriptionElement(notebook.description, role)
    );
    noteCtn.appendChild(genUpdateBtnCtn(notebook.id));
    noteCtn.appendChild(await NoteRender.genNoteCardCtn(notebook.id));
    return noteCtn;

    function genNotebookDescriptionElement(description, role) {
      const descriptionElement = document.createElement("p");
      descriptionElement.classList.add("description");
      descriptionElement.setAttribute("contenteditable", true);
      descriptionElement.textContent = description;
      if (role === "owner") {
        descriptionElement.addEventListener("click", editMode);
      }
      return descriptionElement;
    }
    function genNotebookNameElement(name, role) {
      const nameElement = document.createElement("h4");
      nameElement.setAttribute("contenteditable", true);
      nameElement.textContent = name;
      if (role === "owner") {
        nameElement.addEventListener("click", editMode);
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

    function genLeaveCoNotebookBtn(notebookId) {
      const leaveBtn = document.createElement("div");
      leaveBtn.textContent = "Leave";
      leaveBtn.classList.add("leaveCoNotebookBtn");
      leaveBtn.addEventListener("click", leaveCoNotebook);
      return leaveBtn;

      function leaveCoNotebook() {
        const path = `/api/notebooks/${notebookId}?userEmail=${localStorage.getItem(
          "email"
        )}`;
        FetchDataHandler.fetchData(path, "DELETE");
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
      const name = noteCtn.querySelector("h4");
      description.classList.add("editMode");
      name.classList.add("editMode");
      noteCtn.querySelector(".updateBtn").classList.remove("display-none");
      noteCtn
        .querySelector(".cancelUpdateBtn")
        .classList.remove("display-none");
    }

    function cancelEditMode() {
      const description = noteCtn.querySelector(".description");
      const name = noteCtn.querySelector("h4");
      description.classList.remove("editMode");
      name.classList.remove("editMode");
      noteCtn.querySelector(".updateBtn").classList.add("display-none");
      noteCtn.querySelector(".cancelUpdateBtn").classList.add("display-none");
      description.textContent = notebook.description;
      name.textContent = notebook.name;
    }
  };

  static genNoteCtnWithFilter() {}
  static renderCreateNotebookForm = function () {
    const section = document.createElement("section");
    section.innerHTML = `
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

    section.classList.add("notebookArea");
    MainRender.renderMain(section);
  };
}

class NoteRender {
  constructor() {}
  static genNoteCardCtn = async function (notebookId) {
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
  };

  static genNoteCard(note, notebookId) {
    const noteCard = document.createElement("a");
    noteCard.classList.add("noteCard");
    noteCard.href = `/notebooks/${notebookId}/notes/${note.noteId}`;
    noteCard.appendChild(
      genStar(note.name, note.star, notebookId, note.noteId)
    );
    noteCard.appendChild(genNoteName(note.name));
    noteCard.appendChild(genContent(note.content));
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

    function genContent(text) {
      const contentElement = document.createElement("p");
      contentElement.textContent = text;
      contentElement.classList.add("content");
      return contentElement;
    }

    function genCreateTime(time) {
      time = time.split(" ")[0];
      const timeElement = document.createElement("p");
      timeElement.textContent = time;
      timeElement.classList.add("createTime");
      return timeElement;
    }
  }
}
