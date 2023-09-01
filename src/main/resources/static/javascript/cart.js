$(function () {
  fetchCart();
});

function fetchCart() {
  const username = $("#current-user").text();

  $.ajax({
    url: `/api/user/${username}/cart`,
    type: "GET",
    dataType: "json",
    success: (result) => {
      const cart = result.result;
      cart === null ? renderEmptyCart() : renderCart(cart);
    },
  });
}

function renderEmptyCart() {
  $("#main-container").html(`
    <div class="d-flex justify-content-center align-items-center text-body-tertiary" style="height: 70vh">
      <div class="m-auto">
      <svg xmlns="http://www.w3.org/2000/svg" width="140" height="140" fill="currentColor" class="bi bi-cart-x mx-auto d-block mb-3 " viewBox="0 0 16 16">
        <path d="M7.354 5.646a.5.5 0 1 0-.708.708L7.793 7.5 6.646 8.646a.5.5 0 1 0 .708.708L8.5 8.207l1.146 1.147a.5.5 0 0 0 .708-.708L9.207 7.5l1.147-1.146a.5.5 0 0 0-.708-.708L8.5 6.793 7.354 5.646z"/>
        <path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zm3.915 10L3.102 4h10.796l-1.313 7h-8.17zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
      </svg>  
      <h5>Your cart is empty</h5></div>
    </div>`);
}

function renderCart({ items, total, deliveryMethod, paymentMethod }) {
  const numberFormat = new Intl.NumberFormat("id-ID", {
    style: "currency",
    currency: "IDR",
  });

  $("#delivery-method").val(deliveryMethod);
  $("#payment-method").val(paymentMethod);
  $("#total-price").text(numberFormat.format(total));

  $.each(items, (index, i) => {
    const { productId, productName, productImage, price, quantity, subtotal } =
      i;

    $("#item-container").append(`
      <li class="card shadow-sm mb-3 p-4">
        <div class="row g-0 align-items-center gx-5">
          <div class="col-2 d-inline">
            <img
              width="100%"
              style="object-fit: cover; aspect-ratio: 1/1"
              src="${productImage}"
              alt="${productName}"/>
          </div>
          <div class="col-5">
            <a class="link-underline-opacity-0 link-dark" href="product/${productId}">
              <h5>${productName}</h5>
            </a>
            <span>${numberFormat.format(price)}</span>
          </div>
          <div class="col-2 text-center">Quantity:<br />${quantity}</div>
          <div class="col-3 text-end">${numberFormat.format(subtotal)}</div>
        </div>
      </li>`);
  });

  checkDeliveryAndPayment();
  applyEventListener();
}

function applyEventListener() {
  $("#delivery-method").change(() => {
    const deliveryMethod = $("#delivery-method").val();
    const body = JSON.stringify({ deliveryMethod });
    patchCartMethod(body);
    checkDeliveryAndPayment();
  });

  $("#payment-method").change(() => {
    const paymentMethod = $("#payment-method").val();
    const body = JSON.stringify({ paymentMethod });
    patchCartMethod(body);
    checkDeliveryAndPayment();
  });

  $("#checkout-button").click(() => {
    const username = $("#current-user").text();

    $.ajax({
      url: `/api/user/${username}/cart/checkout`,
      type: "PATCH",
      success: () => {
        window.location.replace(`${window.location.origin}/order-history`);
      },
    });
  });

  function patchCartMethod(body) {
    const username = $("#current-user").text();

    $.ajax({
      url: `/api/user/${username}/cart`,
      type: "PATCH",
      dataType: "json",
      contentType: "application/json",
      data: body,
    });
  }
}

function checkDeliveryAndPayment() {
  const deliveryMethod = $("#delivery-method").val();
  const paymentMethod = $("#payment-method").val();

  if (deliveryMethod === null || paymentMethod === null) {
    $("#checkout-button").attr("disabled", true);
  } else $("#checkout-button").attr("disabled", false);
}
