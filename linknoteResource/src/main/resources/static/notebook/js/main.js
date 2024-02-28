class MainRender {
  constructor() {}

  static renderMain(htmlElement) {
    const main = document.querySelector("main");
    main.innerHTML = "";
    main.appendChild(htmlElement);
  }
}
