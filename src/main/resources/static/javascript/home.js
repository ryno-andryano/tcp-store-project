$(function () {
  $.ajax({
    url: "/api/product/latest",
    type: "GET",
    dataType: "json",
    success: (result) => {
      const products = result.result;
      let productsHtml = "";

      const numberFormat = new Intl.NumberFormat("id-ID", {
        style: "currency",
        currency: "IDR",
      });

      for (let i = 0; i < 3; i++) {
        productsHtml += `
        <a href="/product/${
          products[i].id
        }" class="col link-dark link-underline-opacity-0">
          <div class="card shadow-sm">
            <img
              class="bd-placeholder-img card-img-top"
              height="470px"
              style="object-fit: cover"
              src="${products[i].image}"
              alt="${products[i].name}"
            />
            <div class="card-body">
              <h4 class="card-text text-truncate">${products[i].name}</h4>
              <div class="cart-text">${numberFormat.format(
                products[i].price,
              )}</div>
            </div>
          </div>
        </a>`;

        $("#featured-container").html(productsHtml);
      }
    },
  });
});
