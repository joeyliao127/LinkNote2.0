class NoteMain {
  constructor() {
    this.#init();
  }
  async #init() {
    this.#notebookId = this.#getId("notebook");
    this.#noteId = this.#getId("note");
    this.#notes = await this.#getNotes();
    this.#renderNoteCtn();
    this.#renderSideBar(this.#notes);
    this.#renderTopBar(this.#notes);
  }

  #filter = {
    limit: 20,
    offset: 0,
    star: false,
    sortDesc: false,
    tag: null,
    keyword: null,
  };

  #setFilter(key, value) {
    this.#filter[key] = value;
    this.#renderNoteCtn();
  }

  #notebookId;
  #noteId;
  #notes;
  #getId(target) {
    const url = window.location.href.split("/");

    if (target === "note") {
      return url[url.length - 1];
    } else {
      return url[url.length - 3];
    }
  }

  async #getNotes() {
    let offset = 0;
    let notes = {
      notes: [],
      tags: [],
      notebookName: null,
    };
    while (true) {
      const path = `/api/notebooks/${this.#notebookId}/notes?limit=${
        this.#filter.limit
      }&offset=${offset}&sortDesc=${this.#filter.sortDesc}&tag=${
        this.#filter.tag
      }&keyword=${this.#filter.keyword}`;

      const response = await FetchDataHandler.fetchData(path, "GET");

      if (!response.ok) {
        MessageMaker.failed("Error: Get notes failed");
        throw new Error("Error: Get notes failed");
      }

      const data = await response.json();

      if (offset === 0) {
        notes.notebookName = data.notebookName;
        notes.tags.push(...data.tags);
      }

      notes.notes.push(...data.notes);

      if (!data.nextPage) {
        return notes;
      }
      offset += 20;
    }
  }

  #renderSideBar() {
    hiddenSideBarBtnListener();
    setNotebookName(this.#notes.notebookName);
    setUserEmail();
    this.#renderNotebookTags();
    tagBtnListener();
    displayFilterBtnListener();

    document
      .querySelector(".searchNote input")
      .addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
          const keyword = document.querySelector(".searchNote input").value;
          this.#setFilter("keyword", keyword);
        }
      });

    document.querySelector(".searchNote img").addEventListener("click", () => {
      const keyword = document.querySelector(".searchNote input").value;
      this.#setFilter("keyword", keyword);
    });

    function hiddenSideBarBtnListener() {
      document
        .querySelector("#hiddenSideBarBtn")
        .addEventListener("click", () => {
          document.querySelector(".sideBar").classList.remove("displaySideBar");
        });
    }

    function setNotebookName(notebookName) {
      document.querySelector(".notebookName").textContent = notebookName;
    }

    function displayFilterBtnListener() {
      document.querySelector(".filter").addEventListener("click", () => {
        document.querySelector(".filterCtn").classList.toggle("display-none");
        document.querySelector(".notebookTagCtn").classList.add("display-none");
      });
    }

    function tagBtnListener() {
      document
        .querySelector(".notebookTagBtn")
        .addEventListener("click", () => {
          document
            .querySelector(".notebookTagCtn")
            .classList.toggle("display-none");
          document.querySelector(".filterCtn").classList.add("display-none");
        });
    }
    function setUserEmail() {
      document.querySelector(".userEmail p").textContent =
        localStorage.getItem("email");
    }
  }

  #renderNotebookTags() {
    const tagArea = document.querySelector(".tagArea");

    this.#notes.tags.forEach((tag) => {
      tagArea.appendChild(genTag(this.#notebookId, tag));
    });

    createTagBtnListener(this.#notebookId);

    function genTag(notebookId, tag) {
      const tagItem = document.createElement("div");
      tagItem.classList.add("tag");
      tagItem.innerHTML = `
        <p>${tag.name}</p>
        <div class="deleteTagBtn">
          <p>Removev</p>
        </div>
      `;
      tagItem
        .querySelector(".deleteTagBtn")
        .addEventListener("click", async () => {
          const path = `/api/notebooks/${notebookId}/tags?tagId=${tag.tagId}`;
          const response = await FetchDataHandler.fetchData(path, "DELETE");
          if (response.ok) {
            MessageMaker.success(`Remove tag ${tag.name} success!`);
            tagItem.remove();
          }
        });
      return tagItem;
    }

    function createTagBtnListener(notebookId) {
      const input = document.querySelector(".createTagForm input");
      document
        .querySelector(".createTagForm input")
        .addEventListener("keypress", (e) => {
          if (e.key === "Enter") {
            const tagName = document.querySelector(
              ".createTagForm input"
            ).value;
            createNotebookTag(tagName, notebookId);
            input.value = "";
          }
        });

      document.querySelector("#createTagBtn").addEventListener("click", () => {
        const tagName = document.querySelector(".createTagForm input").value;
        createNotebookTag(tagName, notebookId);
        input.value = "";
      });

      async function createNotebookTag(tagName, notebookId) {
        const path = `/api/notebooks/${notebookId}/tags`;
        const response = await FetchDataHandler.fetchData(path, "POST", {
          name: tagName,
        });
        if (response.ok) {
          MessageMaker.success("Create tag Success!");
        } else {
          MessageMaker.success("Create tag Failed!");
        }

        const tagId = await response.json();
        const tag = {
          name: tagName,
          tagId: tagId,
        };
        tagArea.appendChild(genTag(notebookId, tag));
      }
    }
  }

  async #renderNoteCtn() {
    const data = await this.#getNotes();
    const notes = data.notes;
    const noteArea = document.querySelector(".noteArea");
    noteArea.innerHTML = "";
    notes.forEach((note) => {
      noteArea.appendChild(genNote(note, this.#notebookId));
    });

    function genNote(note, notebookId) {
      const noteItem = document.createElement("div");
      noteItem.classList.add("note");
      noteItem.innerHTML = `
        <p>${note.name}</p>
        <div class="star"></div>
      `;
      if (note.star) {
        noteItem.querySelector(".star").classList.add("star-full");
      } else {
        noteItem.querySelector(".star").classList.add("star-empty");
      }
      updateStarBtnListener(note.noteId, note.name, notebookId);
      return noteItem;

      function updateStarBtnListener(noteId, noteName, notebookId) {
        const starBtn = noteItem.querySelector(".star");
        let requestBody;
        starBtn.addEventListener("click", async () => {
          const path = `/api/notebooks/${notebookId}/notes/${noteId}`;

          if (starBtn.classList.contains("star-full")) {
            noteItem.querySelector(".star").classList.remove("star-full");
            noteItem.querySelector(".star").classList.add("star-empty");
            requestBody = {
              name: noteName,
              star: false,
            };
          } else {
            noteItem.querySelector(".star").classList.add("star-full");
            noteItem.querySelector(".star").classList.remove("star-empty");
            requestBody = {
              name: noteName,
              star: true,
            };
          }

          const response = await FetchDataHandler.fetchData(
            path,
            "PUT",
            requestBody
          );
          if (!response.ok) {
            MessageMaker.failed("Error: update star Failed.");
            return;
          }
        });
      }
    }
  }

  #renderTopBar(notes) {
    hiddenSideBar();
    function hiddenSideBar() {
      document.querySelector("#hamburger").addEventListener("click", () => {
        document.querySelector(".sideBar").classList.add("displaySideBar");
      });
    }
  }
}

const noteMain = new NoteMain();
