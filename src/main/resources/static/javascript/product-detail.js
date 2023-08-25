$(function () {
  $.ajax({
    url: `/api${window.location.pathname}`,
    type: "GET",
    dataType: "json",
    error: (xhr) => {
      if (xhr.status === 404) {
        window.location.replace(`${window.location.origin}/error404`);
      }
    },
    success: (result) => {
      const { name, price, image, description } = result.result;

      $("#product-name").text(name);

      const numberFormat = new Intl.NumberFormat("id-ID", {
        style: "currency",
        currency: "IDR",
      });
      $("#product-price").text(numberFormat.format(price));

      $("#product-description").text(description);

      let imagesHtml = "";
      $.each(image, (index, i) => {
        imagesHtml += `
        <img
            class="col"
            height="450px"
            style="object-fit: cover"
            src="${i}"
            alt="${name}"
        />`;
      });
      $("#images-container").html(imagesHtml);
    },
  });

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
});
