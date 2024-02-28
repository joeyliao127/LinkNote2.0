class NotebookRander {
  constructor() {}

  static renderNotebookMain = function (element) {
    const notebookArea = document.querySelector(".notebookArea");
    notebookArea.innerHTML = "";
    notebookArea.appendChild(element);
  };

  static renderMyNotebooks = function () {
    const div = document.createElement("div");
    MainRender.renderMain(div);
  };
  static renderNoteook = function () {};

  static renderCoNotebooks = function () {};
  static renderCreateNotebook = function () {
    const initWrapper = document.createElement("div");
    initWrapper.classList.add("initNotebookWrapper");
  };
}
