$(function () {
  fetchProducts();
});

function fetchProducts() {
  const q = new URLSearchParams(window.location.search);
  $("#filter-name").val(q.get("name"));
  $("#filter-min-price").val(q.get("pmin"));
  $("#filter-max-price").val(q.get("pmax"));

  $.ajax({
    url: `/api/products${window.location.search}`,
    type: "GET",
    dataType: "json",
    success: (result) => {
      const products = result.result;
      renderProducts(products);
    },
  });
}

function renderProducts(products) {
  const numberFormat = new Intl.NumberFormat("id-ID", {
    style: "currency",
    currency: "IDR",
  });

  $("#products-container").empty();
  $.each(products, (index, p) => {
    $("#products-container").append(`
      <a href="/product/${
        p.id
      }" class="col link-dark link-underline-opacity-0" id="product-${p.id}">
        <div class="card shadow-sm">
          <img
            class="bd-placeholder-img card-img-top"
            height="320px"
            style="object-fit: cover"
            src="${p.image}"
            alt="${p.name}"
          />
          <div class="card-body">
            <h5 class="card-text text-truncate m-0 pb-1">${p.name}</h5>
            <div class="cart-text">${numberFormat.format(p.price)}</div>
          </div>
        </div>
      </a>`);
  });
}
