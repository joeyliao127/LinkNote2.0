class MessageMaker {
  constructor() {}

  static genMessageBlock = function (message) {
    const messageCtn = document.createElement("div");
    messageCtn.classList.add("messageCtn");
    messageCtn.classList.add("displayMessage");
    const status = document.createElement("div");
    status.classList.add("status");
    const msg = document.createElement("p");
    msg.textContent = message;
    const closeBtn = document.createElement("div");
    closeBtn.id = "close";

    closeBtn.addEventListener("click", () => {
      messageCtn.classList.remove("displayMessage");
    });
    messageCtn.appendChild(status);
    messageCtn.appendChild(msg);
    messageCtn.appendChild(closeBtn);
    return messageCtn;
  };

  static success = function (message) {
    const messageBlock = this.genMessageBlock(message);
    messageBlock.classList.add("success");
    this.appnedMessageBlock(messageBlock);
  };

  static failed = function (message) {
    const messageBlock = this.genMessageBlock(message);
    messageBlock.classList.add("failed");
    this.appnedMessageBlock(messageBlock);
  };

  static warning = function (message) {
    const messageBlock = this.genMessageBlock(message);
    messageBlock.classList.add("warning");
    this.appnedMessageBlock(messageBlock);
  };

  static appnedMessageBlock(messageBlock) {
    setTimeout(() => {
      messageBlock.classList.remove("displayMessage");
    }, 5000);
    document.querySelector("body").appendChild(messageBlock);
  }
}
