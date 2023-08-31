$(function () {
  fetchLatestProduct();
});

function fetchLatestProduct() {
  $.ajax({
    url: "/api/product/latest",
    type: "GET",
    dataType: "json",
    success: (result) => {
      const products = result.result;
      renderLatestProducts(products);
    },
  });
}

function renderLatestProducts(products) {
  const numberFormat = new Intl.NumberFormat("id-ID", {
    style: "currency",
    currency: "IDR",
  });

  $("#featured-container").empty();
  $.each(products, (index, p) => {
    $("#featured-container").append(`
      <a href="/product/${p.id}" class="col link-dark link-underline-opacity-0">
        <div class="card shadow-sm">
          <img
            class="bd-placeholder-img card-img-top"
            height="470px"
            style="object-fit: cover"
            src="${p.image}"
            alt="${p.name}"
          />
          <div class="card-body">
            <h4 class="card-text text-truncate">${p.name}</h4>
            <div class="cart-text">${numberFormat.format(p.price)}</div>
          </div>
        </div>
      </a>`);
  });
}
