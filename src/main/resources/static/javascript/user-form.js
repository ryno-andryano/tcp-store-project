$(function () {
  const pathUsername = getPathUsername();
  if (pathUsername) fetchInitialValue(pathUsername);
  applyEventListener(pathUsername);
});

function getPathUsername() {
  let username;
  const pathArr = window.location.pathname.split("/");
  if (pathArr.length === 4) username = pathArr[2];
  return username;
}

function fetchInitialValue(username) {
  $.ajax({
    url: `/api/user/${username}`,
    type: "GET",
    dataType: "json",
    error: (xhr) => {
      if (xhr.status === 404) {
        window.location.replace(`${window.location.origin}/error404`);
      }
    },
    success: (result) => {
      const { username, role } = result.result;

      $("#username").val(username).prop("readonly", true);
      $("#password").attr("placeholder", "********").prop("required", false);
      $("#role").val(role);
    },
  });

  const a1 = $("#username").val();
  const a2 = $("#password").val();
  const a3 = $("#role").val();
  const body = JSON.stringify({ a1, a2, a3 });
  console.log(body);
}

function applyEventListener(pathUsername) {
  $("#user-form").submit(function (e) {
    e.preventDefault();

    const username = $("#username").val();
    const password = $("#password").val();
    const role = $("#role").val();
    const body = password
      ? JSON.stringify({ username, password, role })
      : JSON.stringify({ username, role });
    const { url, method, redirect } = getUrl(pathUsername);

    $.ajax({
      url: url,
      type: method,
      dataType: "json",
      contentType: "application/json",
      data: body,
      success: () => {
        window.location.replace(redirect);
      },
      error: () => {
        $("#username-error").show();
        $("#username").focus();
      },
    });
  });

  $("#username").change(() => {
    $("#username-error").hide();
  });

  function getUrl(username) {
    const pathname = window.location.pathname;
    let url, method, redirect;
    if (username) {
      url = `/api/user/${username}`;
      method = "PUT";
      redirect = document.referrer;
    } else if (pathname.includes("register")) {
      url = "/api/register";
      method = "POST";
      redirect = `${window.location.origin}/login`;
    } else {
      url = "/api/user";
      method = "POST";
      redirect = `${window.location.origin}/users`;
    }
    console.log(username);
    return { url, method, redirect };
  }
}
