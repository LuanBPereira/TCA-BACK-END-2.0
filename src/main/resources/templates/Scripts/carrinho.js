function fetchProducts() {
    fetch('http://localhost:8080/produtos')
        .then(response => response.json())
        .then(produtos => {
            const productList = document.getElementById('product-list');
            productList.innerHTML = ''; // Limpa a lista antes de adicionar os produtos
            produtos.forEach(produto => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${produto.codigoP}</td>
                    <td>${produto.nome}</td>
                    <td>R$ ${produto.preco.toFixed(2)}</td>
                    <td><button class="add-to-cart" data-id="${produto.codigoP}" data-name="${produto.nome}" data-price="${produto.preco}">Adicionar ao Carrinho</button></td>
                `;
                productList.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Erro ao obter a lista de produtos:', error);
        });
}

function adicionarItemCarrinho(id, nome, preco) {
    fetch('http://localhost:8080/carrinho/adicionar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            produto: {
                codigoP: id,
                nome: nome,
                preco: preco
            },
            quantidade: 1
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao adicionar item ao carrinho');
            }
            console.log('Item adicionado ao carrinho com sucesso!');
            fetchCartItems(); // Atualiza a lista de itens no carrinho
            fetchTotalValue(); // Atualiza o valor total
        })
        .catch(error => {
            console.error('Erro ao adicionar item ao carrinho:', error);
        });
}

document.addEventListener('click', function (event) {
    if (event.target.classList.contains('add-to-cart')) {
        const id = event.target.dataset.id;
        const nome = event.target.dataset.name;
        const preco = event.target.dataset.price;
        adicionarItemCarrinho(id, nome, preco);
    }
});

function fetchCartItems() {
    fetch('http://localhost:8080/carrinho/listar')
        .then(response => response.json())
        .then(itens => {
            const cartList = document.querySelector('.cart-list');
            const subtotalElement = document.getElementById('subtotal');
            const deliveryFeeElement = document.getElementById('delivery-fee');

            cartList.innerHTML = '';
            let subtotal = 0;
            itens.forEach(item => {
                const listItem = document.createElement('li');
                listItem.textContent = `${item.produto.nome} - Quantidade: ${item.quantidade}`;
                cartList.appendChild(listItem);
                subtotal += item.produto.preco * item.quantidade;
            });

            const deliveryFee = 7;
            const subtotalFormatted = subtotal.toFixed(2);
            const deliveryFeeFormatted = deliveryFee.toFixed(2);

            subtotalElement.textContent = 'R$ ' + subtotalFormatted;
            deliveryFeeElement.textContent = 'R$ ' + deliveryFeeFormatted;
        })
        .catch(error => {
            console.error('Erro ao obter itens do carrinho:', error);
        });
}

function fetchTotalValue() {
    fetch('http://localhost:8080/carrinho/valorTotal', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            const totalPriceElement = document.getElementById('total-price');

            if (data && data.total !== undefined) {
                let total = parseFloat(data.total).toFixed(2);
                total = total.replace('R$ ', ''); // Remove o prefixo 'R$' se presente

                totalPriceElement.textContent = 'R$ ' + total; // Adiciona o prefixo 'R$' ao valor total
            } else {
                console.error('Resposta do servidor incompleta');
            }
        })
        .catch(error => {
            console.error('Erro ao calcular o valor total:', error);
        });
}

function limparCarrinho() {
    fetch('http://localhost:8080/carrinho/limpar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao limpar o carrinho');
            }
            console.log('Carrinho limpo com sucesso!');
            fetchCartItems(); // Atualiza a lista de itens no carrinho
            fetchTotalValue(); // Atualiza o valor total
        })
        .catch(error => {
            console.error('Erro ao limpar o carrinho:', error);
        });
}

function redirecionarParaPagamento() {
    fetch('http://localhost:8080/carrinho/listar')
        .then(response => response.json())
        .then(itens => {
            if (itens.length === 0) {
                alert('Não é possível finalizar a compra: carrinho de compras vazio.');
            } else {
                window.location.href = 'pagamento.html';
            }
        })
        .catch(error => {
            console.error('Erro ao obter itens do carrinho:', error);
        });
}

document.getElementById('limpar-carrinho').addEventListener('click', limparCarrinho);

fetchTotalValue();
fetchCartItems();
fetchProducts();