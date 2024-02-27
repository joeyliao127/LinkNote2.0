class FetchDataHandler {
  static fetchPath = "http://127.0.0.1";
  constructor() {}

  static setRequestHeader = function (method, requestBody) {
    if (requestBody) {
      return {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
        method: method,
        body: JSON.stringify(requestBody),
      };
    } else {
      return {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
        method: method,
      };
    }
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
