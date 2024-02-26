class FetchDataHandler {
  static fetchPath = "http://localhost";
  constructor() {}

  static setRequestHeader = function (method, requestBody) {
    if (!requestBody) {
      requestBody = "";
    }
    return {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
      method: method,
      body: JSON.stringify(requestBody),
    };
  };

  static async fetchData(path, method, requestBody) {
    let fullPath = this.fetchPath + path;
    if (requestBody) {
      return await fetch(fullPath, this.setRequestHeader(method, requestBody));
    } else {
      return await fetch(fullPath, this.setRequestHeader(method));
    }
  }
}
