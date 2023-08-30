$(function () {
  const pathname = window.location.pathname;
  const pathArr = pathname.split("/");
  let username = "";
  if (pathArr.length === 4) username = pathArr[2];

  if (username) {
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
  }

  $("#user-form").submit(function (e) {
    e.preventDefault();

    const username = $("#username").val();
    const password = $("#password").val();
    const role = $("#role").val();
    const body = JSON.stringify({ username, password, role });
    const { url, method, redirect } = getUrl();

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

  const getUrl = () => {
    let url, method, redirect;
    if (username) {
      url = `/api/user/${username}`;
      method = "PUT";
      redirect = `${window.location.origin}/home`;
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
  };
});
