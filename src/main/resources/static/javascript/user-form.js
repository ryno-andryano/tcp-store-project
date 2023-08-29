$(function () {
  $("#user-form").submit(function (e) {
    e.preventDefault();

    const username = $("#username").val();
    const password = $("#password").val();
    const role = $("#role").val();
    const body = JSON.stringify({ username, password, role });

    $.ajax({
      url: "/api/register",
      type: "POST",
      dataType: "json",
      contentType: "application/json",
      data: body,
      success: () => {
        window.location.replace(`${window.location.origin}/login`);
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
});
