class SideBarRender {
  #notebookRender = new NotebookRender();
  #createNotebookFrom = new CreateNotebookFormRender();
  #myNotebookBtnOffset = 0;
  #myNotebookBtnLimit = 20;
  #coNotebookBtnOffset = 0;
  #coNotebookBtnLimit = 20;
  #notebooks = {};
  #selectedNotebookBtns = {};
  #selectedBtn = {
    myNotebookBtn: document.querySelector(".sideBarMyNotebookArea .sideBarBtn"),
    coNotebookBtn: document.querySelector(".sideBarCoNotebookArea .sideBarBtn"),
    invitationBtn: document.querySelector(".invitationsBtn"),
    settingBtn: document.querySelector(".settingBtn"),
    selectedElement: null,
  };
  constructor() {
    this.#notebookRender.renderMyNotebooks();
    this.genNotebookBtns("myNotebooks");
    this.setUsernameAndEmail();
    this.genCreateNotebookBtnListner();
    this.myNotebookBtnListner();
    this.coNotebookBtnListner();
    this.invitationPageBtnListner();
    this.settingPageBtnListner();
    this.signoutBtnListener();
  }

  async genNotebookBtns(renderPage) {
    const coNotebookBtnsCtn = document.querySelector(
      "#sideBar-coNotebookBtnCtn"
    );
    const myNotebookBtnsCtn = document.querySelector(
      "#sideBar-myNotebookBtnCtn"
    );
    myNotebookBtnsCtn.innerHTML = "";
    coNotebookBtnsCtn.innerHTML = "";
    let path;
    let notebookBtnsCtn;
    if (renderPage === "myNotebooks") {
      this.#renderSelectBtn("myNotebookBtn");
      notebookBtnsCtn = myNotebookBtnsCtn;
      path = `/api/notebooks?offset=${this.#myNotebookBtnOffset}&limit=${
        this.#myNotebookBtnLimit
      }`;
    } else {
      this.#renderSelectBtn("coNotebookBtn");
      notebookBtnsCtn = coNotebookBtnsCtn;
      path = `/api/coNotebooks?offset=${this.#coNotebookBtnOffset}&limit=${
        this.#coNotebookBtnLimit
      }`;
    }
    this.#notebooks = await this.#getNotebooks(path);
    if (this.#notebooks.length != 0) {
      this.#notebooks.forEach((notebook) => {
        notebookBtnsCtn.appendChild(this.#genNotebookBtn(notebook, renderPage));
      });
    }
  }

  async setUsernameAndEmail() {
    const response = await FetchDataHandler.fetchData("/api/user/info", "GET");
    const data = await response.json();
    document.querySelector("#username").textContent = data.username;
    document.querySelector("#email").textContent = data.email;
    localStorage.setItem("email", data.email);
  }

  genCreateNotebookBtnListner() {
    document
      .querySelector("#createNotebookBtn")
      .addEventListener("click", () => {
        this.#createNotebookFrom.renderCreateNotebookForm();
      });
  }

  myNotebookBtnListner() {
    document
      .querySelector(".sideBarMyNotebookArea .sideBarBtn")
      .addEventListener("click", () => {
        this.#notebookRender.resetFitler();
        this.#notebookRender.renderMyNotebooks();
        if (this.#selectedBtn.selectedElement == "myNotebookBtn") {
          this.#renderSelectNotebookBtn("none");
          return;
        }
        this.#renderSelectBtn("myNotebookBtn");

        this.genNotebookBtns("myNotebooks");
      });
  }

  coNotebookBtnListner() {
    document
      .querySelector(".sideBarCoNotebookArea .sideBarBtn")
      .addEventListener("click", () => {
        this.#notebookRender.resetFilter();
        if (this.#selectedBtn.selectedElement == "coNotebookBtn") {
          this.#renderSelectNotebookBtn("");
          return;
        }
        this.#renderSelectBtn("coNotebookBtn");
        this.#notebookRender.renderCoNotebooks();
        this.genNotebookBtns("coNotebooks");
      });
  }

  invitationPageBtnListner() {
    document.querySelector(".invitationsBtn").addEventListener("click", () => {
      if (this.#selectedBtn.selectedElement == "invitationBtn") {
        return;
      }
      this.#renderSelectBtn("invitationBtn");
    });
  }

  settingPageBtnListner() {
    document.querySelector(".settingBtn").addEventListener("click", () => {
      if (this.#selectedBtn.selectedElement == "settingBtn") {
        return;
      }
      this.#renderSelectBtn("settingBtn");
    });
  }

  signoutBtnListener() {
    document.querySelector(".signoutBtn").addEventListener("click", signout);
    function signout() {
      localStorage.removeItem("token");
      location.href = "/";
    }
  }

  #renderSelectBtn(target) {
    this.#selectedBtn.myNotebookBtn.classList.remove("selected");
    this.#selectedBtn.coNotebookBtn.classList.remove("selected");
    this.#selectedBtn.invitationBtn.classList.remove("selected");
    this.#selectedBtn.settingBtn.classList.remove("selected");
    const element = this.#selectedBtn[target];
    element.classList.add("selected");
    this.#selectedBtn.selectedElement = target;
  }

  #genNotebookBtn(notebook, renderPage) {
    const element = document.createElement("p");
    element.textContent = notebook.name;
    element.dataset.description = notebook.description;
    element.dataset.id = notebook.id;
    this.#selectedNotebookBtns[notebook.id] = element;
    element.addEventListener("click", () => {
      if (renderPage === "myNotebooks") {
        this.#notebookRender.renderNotebook(notebook);
      } else {
        this.#notebookRender.renderCoNotebook(notebook);
      }
      this.#renderSelectNotebookBtn(notebook.id);
    });
    return element;
  }

  #getNotebooks = async function (path) {
    const response = await FetchDataHandler.fetchData(path, "GET");
    if (response.ok) {
      const notebooks = await response.json();

      return notebooks.notebooks;
    } else {
      MessageMaker.failed("Error");
    }
  };

  #renderSelectNotebookBtn(notebookId) {
    console.log(`執行select`);
    for (const [key, value] of Object.entries(this.#selectedNotebookBtns)) {
      if (key === notebookId) {
        value.classList.add("selected666");
      } else {
        value.classList.remove("selected666");
      }
    }
  }
  #observer(element, offset, limit) {
    const observe = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        entry.target.classList.toggle("", entry.isIntersecting);
      });
    });
  }
}

const sideBarRender = new SideBarRender();
