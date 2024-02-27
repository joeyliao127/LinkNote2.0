function main() {
  setUsernameAndEmail();
}

async function setUsernameAndEmail() {
  const response = await FetchDataHandler.fetchData("/api/user/info", "GET");
  const data = await response.json();
  document.querySelector("#username").textContent = data.username;
  //   document.querySelector("#email").textContent = data.email;
}

main();
