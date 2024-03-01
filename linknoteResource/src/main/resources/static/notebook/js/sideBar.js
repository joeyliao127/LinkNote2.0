class SideBarRender {
  constructor() {}
  static selectedBtn = {
    myNotebookBtn: document.querySelector(".sideBarMyNotebookArea .sideBarBtn"),
    coNotebookBtn: document.querySelector(".sideBarCoNotebookArea .sideBarBtn"),
    invitationBtn: document.querySelector(".invitationsBtn"),
    settingBtn: document.querySelector(".settingBtn"),
    selectedElement: null,
  };

  static notebooks = {};
  static sideBarMain() {
    this.genNotebookBnts("#sideBar-myNotebookBtnCtn");
    NotebookCollectionRender.renderMyNotebooks();
    this.setUsernameAndEmail();
    this.genCreateNotebookBtnListner();
    this.myNotebookBtnListner();
    this.coNotebookBtnListner();
    this.invitationPageBtnListner();
    this.settingPageBtnListner();
    this.signoutBtnListener();
  }

  static setUsernameAndEmail = async function () {
    const response = await FetchDataHandler.fetchData("/api/user/info", "GET");
    const data = await response.json();
    document.querySelector("#username").textContent = data.username;
    document.querySelector("#email").textContent = data.email;
    localStorage.setItem("email", data.email);
  };

  static genCreateNotebookBtnListner() {
    document
      .querySelector("#createNotebookBtn")
      .addEventListener("click", () => {
        CreateNotebookFormRender.renderCreateNotebookForm();
        CreateNotebookFormRender.main();
      });
  }

  static myNotebookBtnListner() {
    document
      .querySelector(".sideBarMyNotebookArea .sideBarBtn")
      .addEventListener("click", () => {
        NotebookCollectionRender.renderMyNotebooks();
        if (this.selectedBtn.selectedElement == "myNotebookBtn") {
          return;
        }
        this.genNotebookBnts("#sideBar-myNotebookBtnCtn");
      });
  }

  static coNotebookBtnListner() {
    document
      .querySelector(".sideBarCoNotebookArea .sideBarBtn")
      .addEventListener("click", () => {
        if (this.selectedBtn.selectedElement == "coNotebookBtn") {
          return;
        }
        NotebookCollectionRender.renderCoNotebooks();
        this.genNotebookBnts("#sideBar-coNotebookBtnCtn");
      });
  }

  static genNotebookBnts = async function (id) {
    const coNotebookBtnsCtn = document.querySelector(
      "#sideBar-coNotebookBtnCtn"
    );
    const myNotebookBtnsCtn = document.querySelector(
      "#sideBar-myNotebookBtnCtn"
    );
    myNotebookBtnsCtn.innerHTML = "";
    coNotebookBtnsCtn.innerHTML = "";
    const offset = 0;
    const limit = 20;
    let path;
    let notebookBtnsCtn;
    if (id === "#sideBar-myNotebookBtnCtn") {
      this.renderSelectBtn("myNotebookBtn");
      notebookBtnsCtn = myNotebookBtnsCtn;
      path = `/api/notebooks?offset=${offset}&limit=${limit}`;
    } else {
      this.renderSelectBtn("coNotebookBtn");
      notebookBtnsCtn = coNotebookBtnsCtn;
      path = `/api/coNotebooks?offset=${offset}&limit=${limit}`;
    }
    this.notebooks = await this.getNotebooks(path);
    console.log(this.notebooks);
    if (this.notebooks.length != 0) {
      this.appendNotebookBtns(notebookBtnsCtn, this.notebooks);
    }
  };

  static getNotebooks = async function (path) {
    const response = await FetchDataHandler.fetchData(path, "GET");
    if (response.ok) {
      const notebooks = await response.json();

      return notebooks.notebooks;
    } else {
      MessageMaker.failed("Error");
    }
  };

  static appendNotebookBtns(ctn, notebooks) {
    notebooks.forEach((notebook) => {
      const element = document.createElement("p");
      element.textContent = notebook.name;
      element.dataset.description = notebook.description;
      element.dataset.id = notebook.id;
      ctn.appendChild(element);
    });
  }

  static observer(element, offset, limit) {
    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        entry.target.classList.toggle("", entry.isIntersecting);
      });
    });
  }

  static renderSelectBtn(target) {
    this.selectedBtn.myNotebookBtn.classList.remove("selected");
    this.selectedBtn.coNotebookBtn.classList.remove("selected");
    this.selectedBtn.invitationBtn.classList.remove("selected");
    this.selectedBtn.settingBtn.classList.remove("selected");
    const element = this.selectedBtn[target];
    element.classList.add("selected");
    this.selectedBtn.selectedElement = target;
  }

  static invitationPageBtnListner() {
    document.querySelector(".invitationsBtn").addEventListener("click", () => {
      if (this.selectedBtn.selectedElement == "invitationBtn") {
        return;
      }
      this.renderSelectBtn("invitationBtn");
    });
  }

  static settingPageBtnListner() {
    document.querySelector(".settingBtn").addEventListener("click", () => {
      if (this.selectedBtn.selectedElement == "settingBtn") {
        return;
      }
      this.renderSelectBtn("settingBtn");
    });
  }

  static signoutBtnListener() {
    document.querySelector(".signoutBtn").addEventListener("click", signout);
    function signout() {
      localStorage.removeItem("token");
      location.href = "/";
    }
  }
}

SideBarRender.sideBarMain();
