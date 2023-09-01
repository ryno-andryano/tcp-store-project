$(function () {
  fetchOrders();
});

function fetchOrders() {
  const username = $("#current-user").text();

  $.ajax({
    url: `/api/user/${username}/orders`,
    type: "GET",
    dataType: "json",
    success: (result) => {
      const orders = result.result;
      orders.length > 0 ? renderOrders(orders) : renderEmptyOrders();
    },
  });
}

function renderEmptyOrders() {
  $("#main-container").html(`
    <div class="d-flex justify-content-center align-items-center text-body-tertiary" style="height: 70vh">
      <div class="m-auto">
      <svg xmlns="http://www.w3.org/2000/svg" width="140" height="140" fill="currentColor" class="bi bi-journal-x mx-auto d-block mb-3" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M6.146 6.146a.5.5 0 0 1 .708 0L8 7.293l1.146-1.147a.5.5 0 1 1 .708.708L8.707 8l1.147 1.146a.5.5 0 0 1-.708.708L8 8.707 6.854 9.854a.5.5 0 0 1-.708-.708L7.293 8 6.146 6.854a.5.5 0 0 1 0-.708z"/>
        <path d="M3 0h10a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-1h1v1a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v1H1V2a2 2 0 0 1 2-2z"/>
        <path d="M1 5v-.5a.5.5 0 0 1 1 0V5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0V8h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0v.5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1z"/>
      </svg>
      <h5>You have no order history</h5></div>
    </div>`);
}

function renderOrders(orders) {
  const numberFormat = new Intl.NumberFormat("id-ID", {
    style: "currency",
    currency: "IDR",
  });

  orders.reverse();
  $("#order-accordion").empty();
  $.each(orders, (index, o) => {
    const { orderId, orderDate, items, total, paymentMethod, deliveryMethod } =
      o;

    $("#order-accordion").append(`
      <div class="accordion-item">
        <h2 class="accordion-header">
          <button
            class="accordion-button collapsed"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#flush-collapse-${orderId}"
          >
            <span class="fw-bolder fs-5 me-3">Order #${orderId}</span>
            <span class="my-auto text-body-tertiary"
              >${orderDate}</span
            >
          </button>
        </h2>
        <div
          id="flush-collapse-${orderId}"
          class="accordion-collapse collapse"
          data-bs-parent="#order-accordion"
        >
          <div class="accordion-body py-4 my-2">
            <div class="m-0 p-0">
              <h5>Items:</h5>

              <div id="item-container-${orderId}"></div>
            </div>

            <hr />

            <div class="m-0 mt-4 p-0 row">
              <div class="col">
                <h5>Delivery Method:</h5>
                <span>${deliveryMethod}</span>
              </div>

              <div class="col">
                <h5>Payment Method:</h5>
                <span>${paymentMethod}</span>
              </div>

              <div class="col-2">
                <h5>Total:</h5>
                <span>${numberFormat.format(total)}</span>
              </div>
            </div>
          </div>
        </div>
      </div>`);

    $.each(items, (index, i) => {
      const { productName, price, productImage, quantity, subtotal } = i;

      $(`#item-container-${orderId}`).append(`
        <div class="p-3">
          <div class="row g-0 align-items-center gx-5">
            <div class="col-1 d-inline">
              <img
                width="100%"
                style="object-fit: cover; aspect-ratio: 1/1"
                src="${productImage}"
                alt="${productName}"/>
            </div>
            <div class="col-6">
              <h5>${productName}</h5>
              <span>${numberFormat.format(price)}</span>
            </div>
            <div class="col-2 text-center">
              Quantity:<br />${quantity}
            </div>
            <div class="col-3 text-end">
              ${numberFormat.format(subtotal)}
            </div>
          </div>
        </div>`);
    });
  });
}
