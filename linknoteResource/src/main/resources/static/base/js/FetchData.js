class FetchDataHandler {
  constructor() {}

  static fetchPath = "http://localhost";

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

  static async fetchData(path, method) {
    let fullPath = this.fetchPath + path;
    return await fetch(fullPath, this.setRequestHeader(method));
  }

  static async fetchDataWithRequestBody(path, method, requestBody) {
    let fullPath = this.fetchPath + path;
    return await fetch(fullPath, this.setRequestHeader(method, requestBody));
  }
}
