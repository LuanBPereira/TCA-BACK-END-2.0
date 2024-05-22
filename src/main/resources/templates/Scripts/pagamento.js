function makePayment(formaDePagamento) {
    fetch(`http://localhost:8080/pagamento/${formaDePagamento}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao realizar o pagamento');
            }
            console.log('Pagamento realizado com sucesso!');
            alert(`Pagamento realizado via *${formaDePagamento}* com sucesso!`);

            // Redirecionar o usuário para a página de produtos após o pagamento
            window.location.href = 'carrinho.html';
        })
        .catch(error => {
            console.error('Erro ao realizar o pagamento:', error);
        });
}

window.addEventListener('load', function () {
    fetchOrderDetails();
});

function fetchOrderDetails() {
    fetch('http://localhost:8080/carrinho/listar')
        .then(response => response.json())
        .then(itens => {
            const productsList = document.getElementById('products-list');
            const subtotalElement = document.getElementById('subtotal');
            const deliveryFeeElement = document.getElementById('delivery-fee');
            const totalPriceElement = document.getElementById('total-price');

            productsList.innerHTML = ''; // Limpa a lista antes de adicionar os produtos

            let subtotal = 0;

            itens.forEach(item => {
                const listItem = document.createElement('li');
                listItem.textContent = `${item.produto.nome} - Quantidade: ${item.quantidade} - Preço: R$ ${item.produto.preco.toFixed(2)}`;
                productsList.appendChild(listItem);
                subtotal += item.produto.preco * item.quantidade;
            });

            const deliveryFee = 7;
            const subtotalFormatted = subtotal.toFixed(2);
            const deliveryFeeFormatted = deliveryFee.toFixed(2);
            const total = subtotal + deliveryFee;
            const totalFormatted = total.toFixed(2);

            subtotalElement.textContent = 'R$ ' + subtotalFormatted;
            deliveryFeeElement.textContent = 'R$ ' + deliveryFeeFormatted;
            totalPriceElement.textContent = 'R$ ' + totalFormatted;
        })
        .catch(error => {
            console.error('Erro ao obter detalhes do pedido:', error);
        });
}

fetchOrderDetails();