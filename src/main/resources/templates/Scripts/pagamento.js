function realizarPagamento(formaDePagamento) {
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