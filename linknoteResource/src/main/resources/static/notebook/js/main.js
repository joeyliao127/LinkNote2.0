class MainRender {
  constructor() {}

  static renderMain(htmlElement) {
    const main = document.querySelector("main");
    main.appendChild(htmlElement);
  }
}
