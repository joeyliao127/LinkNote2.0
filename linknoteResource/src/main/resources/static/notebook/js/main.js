class ReRenderElement {
  constructor() {}

  static reRenderMain(element) {
    const main = document.querySelector(".mainwrapper");
    main.innerHTML = "";
    main.appendChild(element);
  }
}
