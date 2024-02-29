class DeleteAlert {
  constructor() {}

  static renderDeleteAletBox(container, target, name, path) {
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

    genDeleteTargetBtn(path);
    genRemoveWrapperBtn();

    function genDeleteTargetBtn(path) {
      const deleteBtn = alertBoxWrapper.querySelector("#delete");
      deleteBtn.addEventListener("click", () => {
        FetchDataHandler.fetchData(path, "DELETE");
        window.location.href = window.location.href;
      });
    }

    function genRemoveWrapperBtn() {
      const cancelBtn = alertBoxWrapper.querySelector("#cancelDelete");
      cancelBtn.addEventListener("click", () => {
        alertBoxWrapper.remove();
      });
    }

    container.appendChild(alertBoxWrapper);
  }
}
