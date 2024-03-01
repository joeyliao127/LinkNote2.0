class ReRender {
  constructor() {}

  static reRenderMain(element) {
    const main = document.querySelector("main");
    main.innerHTML = "";
    main.appendChild(element);
  }
}
