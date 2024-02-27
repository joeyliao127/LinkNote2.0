function createNotebookMain() {
  createNotebookTagBtnListener();
}

function createNotebookTagBtnListener() {
  document.querySelector("#addTag").addEventListener("click", generateTags);

  function generateTags() {
    MessageMaker.success("新增tag成功！");
    const tag = document.createElement("p");
    tag.textContent = document.querySelector("#tag").value;
    document.querySelector("#tag").value = "";
    tag.addEventListener("click", () => {
      tag.remove();
    });

    document.querySelector(".tags").appendChild(tag);
  }
}

function createNotebookHandler() {
  const tags = [];
  document.querySelectorAll(".tags p").forEach((item) => {
    tags.push(item.value);
  });
  const requestBody = {
    notebookName: document.querySelector("#notebookName").value,
    description: document.querySelector("#description").value,
    tags: tags,
  };

  FetchDataHandler.fetchData("/api/notebooks", "POST", requestBody);
}

function renderCreateNotebookForm() {
  elementHTML = `
  <section class="createNotebookWrapper">
  <h3>Create Notebook</h3>
  <div class="createNotebookForm">
    <div class="inputs">
      <label for="notebookName">Notebook Name</label>
      <input
        type="text"
        name="notebookName"
        id="notebookName"
        placeholder="Name"
      />
      <label for="description">Description (Optional)</label>
      <input
        type="text"
        name="description"
        id="description"
        placeholder="What is the purpose of the notebook?"
      />
      <label for="tag">Tags</label>
      <div class="tagInput">
        <input type="text" name="tag" id="tag" placeholder="Tag Name" />
        <button id="addTag">Add</button>
      </div>
      <div class="tags"></div>
    </div>

    <div class="flex createNotebookBtns">
      <div id="cancel" class="no-select">Cancel</div>
      <div id="submit" class="no-select">Submit</div>
    </div>
  </div>
</section>`;
  MainRender.renderMain(elementHTML);
  createNotebookMain();
}
