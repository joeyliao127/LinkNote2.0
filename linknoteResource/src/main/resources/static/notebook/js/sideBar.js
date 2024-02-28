function sideBarMain() {
  setUsernameAndEmail();
  createNotebookBtnListner();
  signoutBtnListener();
}

async function setUsernameAndEmail() {
  const response = await FetchDataHandler.fetchData("/api/user/info", "GET");
  const data = await response.json();
  document.querySelector("#username").textContent = data.username;
  document.querySelector("#email").textContent = data.email;
}

function createNotebookBtnListner() {
  document
    .querySelector("#createNotebookBtn")
    .addEventListener("click", renderCreateNotebookForm);
}

function signoutBtnListener() {
  document.querySelector(".signoutBtn").addEventListener("click", signout);
  function signout() {
    localStorage.removeItem("token");
    location.href = "/";
  }
}
sideBarMain();
