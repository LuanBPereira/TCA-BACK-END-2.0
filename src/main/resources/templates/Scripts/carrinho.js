// Função para buscar e exibir os produtos
function fetchProducts() {
    fetch('http://localhost:8080/produtos')
        .then(response => response.json())
        .then(produtos => {
            const productList = document.getElementById('product-list');
            produtos.forEach(produto => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${produto.codigoP}</td>
                    <td>${produto.nome}</td>
                    <td>R$ ${produto.preco.toFixed(2)}</td>
                    <button class="add-to-cart" data-id="${produto.codigoP}" data-name="${produto.nome}" data-price="${produto.preco}">Adicionar ao Carrinho</button>
                `;
                productList.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Erro ao obter a lista de produtos:', error);
        });
}

// Função para adicionar um item ao carrinho
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
            // Atualizar a lista de itens do carrinho após adicionar ao carrinho
            fetchCartItems();
            // Chamar a função para buscar o valor total após adicionar um item ao carrinho
            fetchTotalValue();
        })
        .catch(error => {
            console.error('Erro ao adicionar item ao carrinho:', error);
        });
}
// Evento de clique para adicionar um item ao carrinho
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
// Função para limpar o carrinho
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
            // Atualizar a lista de itens do carrinho após limpar
            fetchCartItems();
            // Chamar a função para buscar o valor total após limpar o carrinho
            fetchTotalValue();
        })
        .catch(error => {
            console.error('Erro ao limpar o carrinho:', error);
        });
}

function redirecionarParaPagamento() {
    // Verificar se o carrinho está vazio antes de redirecionar para a página de pagamento
    fetch('http://localhost:8080/carrinho/listar')
        .then(response => response.json())
        .then(itens => {
            if (itens.length === 0) {
                // Se o carrinho estiver vazio, exibir um alerta e não redirecionar
                alert('Não é possível finalizar a compra: carrinho de compras vazio.');
            } else {
                // Se o carrinho não estiver vazio, redirecionar para a página de pagamento
                window.location.href = 'pagamento.html';
            }
        })
        .catch(error => {
            console.error('Erro ao obter itens do carrinho:', error);
        });
}
// Evento de clique para limpar o carrinho
document.getElementById('limpar-carrinho').addEventListener('click', limparCarrinho);
// Chamada da função para buscar os itens do carrinho ao carregar a página
fetchTotalValue();
fetchCartItems();
fetchProducts();
