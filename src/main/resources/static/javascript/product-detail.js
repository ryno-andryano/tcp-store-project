$(function () {
  fetchProductDetail();
  applyEventListener();
});

function fetchProductDetail() {
  $.ajax({
    url: `/api${window.location.pathname}`,
    type: "GET",
    dataType: "json",
    error: (xhr) => {
      window.location.replace(`${window.location.origin}/error${xhr.status}`);
    },
    success: (result) => {
      const product = result.result;
      renderProductDetail(product);
    },
  });
}

function renderProductDetail({ name, price, image, description }) {
  const numberFormat = new Intl.NumberFormat("id-ID", {
    style: "currency",
    currency: "IDR",
  });

  $("#product-name").text(name);
  $("#product-price").text(numberFormat.format(price));
  $("#product-description").text(description);
  $.each(image, (index, i) => {
    $("#images-container").append(`
        <img
            class="col"
            height="450px"
            style="object-fit: cover"
            src="${i}"
            alt="${name}"
        />`);
  });
}

function applyEventListener() {
  $("#edit-button").click(() => {
    window.location.href += "/edit";
  });

  $("#delete-button").click(() => {
    const confirmation = confirm(
      "Are you sure you want to remove this product?",
    );
    if (confirmation) {
      $.ajax({
        type: "DELETE",
        dataType: "json",
        url: `/api${window.location.pathname}`,
        success: () => {
          window.location.replace(`${window.location.origin}/products`);
        },
      });
    }
  });
}
