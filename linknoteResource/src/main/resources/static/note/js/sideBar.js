class NoteMain {
  constructor() {
    this.#notebookId = this.#getId("notebook");
    this.#noteId = this.#getId("note");
    this.notes = this.#getNotes;
  }

  #notebookId;
  #noteId;
  #getId(target) {
    const url = window.location.href;
    url.split("/");
    if (target === "note") {
      return url[url.length - 1];
    } else {
      return url[url.length - 3];
    }
  }

  #getNotes(notebookId) {
    const path = `/api/notebooks/${notebookId}`;
    const response = FetchDataHandler.fetchData(path, "GET").then((res) => res);
    if (!response.ok) {
    }
  }
}

const noteMain = new NoteMain();
