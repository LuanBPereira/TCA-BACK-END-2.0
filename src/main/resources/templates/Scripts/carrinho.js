document.addEventListener('DOMContentLoaded', () => {
    fetchProducts();

    document.addEventListener('click', function (event) {
        if (event.target.classList.contains('add-to-cart')) {
            const id = event.target.dataset.id;
            const nome = event.target.dataset.name;
            const preco = event.target.dataset.price;
            openAddCartModal(id, nome, preco);
        } else if (event.target.classList.contains('increment')) {
            const id = event.target.dataset.id;
            changeQuantity(id, 1);
        } else if (event.target.classList.contains('decrement')) {
            const id = event.target.dataset.id;
            changeQuantity(id, -1);
        }
    });

    document.getElementById('close-modal').addEventListener('click', closeAddCartModal);
    document.getElementById('increment-modal').addEventListener('click', () => changeModalQuantity(1));
    document.getElementById('decrement-modal').addEventListener('click', () => changeModalQuantity(-1));
    document.getElementById('confirm-add-cart').addEventListener('click', confirmAddCart);

    fetchTotalValue();
    fetchCartItems();
});

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

function openAddCartModal(id, nome, preco) {
    const modal = document.getElementById('add-cart-modal');
    modal.dataset.id = id;
    modal.dataset.nome = nome;
    modal.dataset.preco = preco;
    document.getElementById('modal-product-name').textContent = nome;
    document.getElementById('modal-product-quantity').textContent = 1;
    modal.style.display = 'block';
}

function closeAddCartModal() {
    const modal = document.getElementById('add-cart-modal');
    modal.style.display = 'none';
}

function changeModalQuantity(delta) {
    const quantityElement = document.getElementById('modal-product-quantity');
    let quantity = parseInt(quantityElement.textContent);
    quantity = Math.max(1, quantity + delta);
    quantityElement.textContent = quantity;
}

function confirmAddCart() {
    const modal = document.getElementById('add-cart-modal');
    const id = modal.dataset.id;
    const nome = modal.dataset.nome;
    const preco = modal.dataset.preco;
    const quantidade = parseInt(document.getElementById('modal-product-quantity').textContent);

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
            quantidade: quantidade
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao adicionar item ao carrinho');
            }
            console.log('Item adicionado ao carrinho com sucesso!');
            closeAddCartModal();
            fetchCartItems(); // Atualiza a lista de itens no carrinho
            fetchTotalValue(); // Atualiza o valor total
        })
        .catch(error => {
            console.error('Erro ao adicionar item ao carrinho:', error);
        });
}

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
                listItem.innerHTML = `
                    ${item.produto.nome} - Quantidade: <span id="quantity-${item.produto.codigoP}">${item.quantidade}</span>
                    <button class="decrement" data-id="${item.produto.codigoP}">-</button>
                    <button class="increment" data-id="${item.produto.codigoP}">+</button>
                `;
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

function changeQuantity(codigoProduto, delta) {
    const url = delta > 0 ? `http://localhost:8080/carrinho/incrementar/${codigoProduto}` : `http://localhost:8080/carrinho/decrementar/${codigoProduto}`;
    fetch(url, { method: 'PUT' })
        .then(() => {
            fetchCartItems();
            fetchTotalValue();
        })
        .catch(error => {
            console.error('Erro ao atualizar quantidade:', error);
        });
}

document.getElementById('limpar-carrinho').addEventListener('click', limparCarrinho);
