class AuthorizationHandler {
  constructor() {
    this.token = localStorage.getItem("token");
    if (token) {
      this.verifyUserToken(token);
    } else {
      window.location.href = "/";
    }
  }

  static fetchPath = "http://localhost";

  verifyUserToken = async function (token) {
    const response = await fetch("http://localhost:8080/api/auth/user/token", {
      headers: {
        Authorization: "Bearer " + token,
        "Content-Type": "application/json",
      },
      method: "POST",
    });
    const verifyResult = await response.json();
    if (!verifyResult) {
      localStorage.removeItem("token");
      location.href = "/";
    }
  };
}

class fetchDataHandler {
  constructor() {}

  static async fetchData(path, method, body) {
    const response = await fetch(path, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
      method: method,
      body: JSON.stringify(body),
    });
    if (response.status === 401) {
      window.location.href = "/notebooks";
    }
    return response.json();
  }
}

// async function verifyUserToken(token) {
//   const response = await fetch("http://localhost:8080/api/auth/user/token", {
//     headers: {
//       Authorization: "Bearer " + token,
//       "Content-Type": "application/json",
//     },
//     method: "POST",
//   });
//   const verifyResult = await response.json();
//   if (!verifyResult) {
//     localStorage.removeItem("token");
//     location.href = "/";
//   }
// }

function verifyEmailRegx(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}
baseInit();
