$(function () {
  const pathId = getPathId();
  if (pathId) fetchInitialValue(pathId);
  applyEventListener(pathId);
});

function getPathId() {
  let id;
  const pathArr = window.location.pathname.split("/");
  if (pathArr.length === 4) id = pathArr[2];
  return id;
}

function fetchInitialValue(id) {
  $.ajax({
    url: `/api/product/${id}`,
    type: "GET",
    dataType: "json",
    error: (xhr) => {
      if (xhr.status === 404) {
        window.location.replace(`${window.location.origin}/error404`);
      }
    },
    success: (result) => {
      const { id, name, price, image, description } = result.result;

      $("#id").val(id);
      $("#name").val(name);
      $("#price").val(price);
      $("#image").val(image.join(",\n\n"));
      $("#description").val(description);
    },
  });
}

function applyEventListener(id) {
  $("#product-form").submit(function (e) {
    e.preventDefault();

    const name = $("#name").val();
    const price = $("#price").val();
    const description = $("#description").val();
    const image = $("#image")
      .val()
      .split(new RegExp("\\s*,\\s*"))
      .filter((str) => str);
    const body = JSON.stringify({ name, price, description, image });

    $.ajax({
      url: id ? `/api/product/${id}` : "/api/product",
      type: id ? "PUT" : "POST",
      dataType: "json",
      contentType: "application/json",
      data: body,
      success: () => {
        window.location.replace(`${window.location.origin}/products`);
      },
    });
  });
}
