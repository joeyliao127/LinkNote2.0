class DeleteAlert {
  constructor() {}

  static #genDeleteTargetBtn(path) {
    const deleteBtn = alertBoxWrapper.querySelector("#delete");
    deleteBtn.addEventListener("click", () => {
      FetchDataHandler.fetchData(path, "DELETE");
      window.location.href = window.location.href;
    });
  }

  static #genRemoveWrapperBtn() {
    const cancelBtn = alertBoxWrapper.querySelector("#cancelDelete");
    alertBoxWrapper.remove();
    cancelBtn.addEventListener("click", () => {});
  }

  static renderDeleteAlertBox(target, name) {
    const main = document.querySelector("main");
    const alertBoxWrapper = document.createElement("div");
    alertBoxWrapper.classList.add("alertWrapper");
    alertBoxWrapper.innerHTML = `
    <div class="alertBox">
              <p>
                Delete ${target} "${name}" ?
              </p>
              <div class="deleteBtns">
                <div id="delete">Delete</div>
                <div id="cancelDelete">Cancel</div>
              </div>
            </div>
    `;

    this.#genRemoveWrapperBtn();
    main.appendChild(alertBoxWrapper);
  }
}
