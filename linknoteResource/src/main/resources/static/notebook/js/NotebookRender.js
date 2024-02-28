class NotebookRander {
  constructor() {}

  static renderNotebookMain = function (element) {
    const notebookArea = document.querySelector(".notebookArea");
    notebookArea.innerHTML = "";
    notebookArea.appendChild(element);
  };

  static renderMyNotebooks = function () {
    const section = document.createElement("section");
    MainRender.renderMain(section);
  };
  static renderNoteook = function () {};

  static renderCoNotebooks = function () {};
  static renderCreateNotebook = function () {
    const mainWrapper = document.createElement("diiv");
    mainWrapper.innerHTML = `
          <section class="notebookArea">
            <!-- <h3>My Notebooks</h3> -->
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
          </section>
    `;

    mainWrapper.classList.add("mainWrapper");
    MainRender.renderMain(mainWrapper);
  };
}
