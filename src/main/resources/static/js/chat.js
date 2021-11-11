const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let selectedChat;
let loggedUser;

function connectToChat(userId) {
    console.log("connecting to chat...")
    loggedUser = userId;
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userId, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser == data.de && selectedChat == data.doacao) {
                render(data);
            } else {
                let isNew = document.getElementById("newMessage_" + data.de + "_" + data.doacao) == null;
                if(isNew)
                    $('#userAppender_' + data.de + '_' + data.doacao).append('<span id="newMessage_' + data.de + '_' + data.doacao + '" style="color: red">nova mensagem...</span>');
            }
        });
    });
}

function sendMsg(from, text) {
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        id: 0,
        doacao: selectedChat,
        texto: text,
        de: from,
        para: selectedUser,
        createdAt: getCurrentTime()
    }));
}

function selectUser(doacaoId, connectNome, connectId, ativo, isDoador) {
    if(selectedChat != doacaoId) {
        let botaoChat;
        if(isDoador){
            botaoChat = $("#desativar-chat");

            botaoChat.css({"display": "initial"});
            botaoChat.val(doacaoId);
        }else{
            botaoChat = $("#apagar-chat");

            if(ativo == 'false'){
                botaoChat.css({"display": "initial"});
                botaoChat.val(doacaoId);
                window.alert("O doador finalizou este chat! Você não receberá mais mensagens\nPara retirá-lo da sua lista de contatos utilize o botão 'Finalizar Chat'!");
            }else{
                botaoChat.css({"display": "none"});
            }
        }

        console.log("Conectando a: " + connectNome);
        console.log("Com o id: " + connectId);
        selectedUser = connectId;
        selectedChat = doacaoId;

        $.getJSON(url + "/historico/" + doacaoId, function (data, status) {
            renderChatHistory(data);
        });

        let isNew = document.getElementById("newMessage_" + connectId + "_" + doacaoId) !== null;
        if (isNew) {
            let element = document.getElementById("newMessage_" + connectId + "_" + doacaoId);
            element.parentNode.removeChild(element);
        }
        $('#selectedUserId').html('');
        $('#selectedUserId').append('Conversando com ' + connectNome);
    }
}

function handleChatDeactivation(valor) {
    if(window.confirm("Está certo de finalizar esta conversa?! Ela será removida da sua lista de contatos!")){
        $.ajax({
            success: function (data, status) {
                window.alert(data);
                location.reload();
            },
            type: 'PATCH',
            url: `${url}/doacao/desativar/${valor}`
        });
    }
}

function handleChatDelete(valor) {
    if(window.confirm("Está certo de finalizar esta conversa?! Ela será removida da sua lista de contatos!")){
        $.ajax({
            success: function (data, status) {
                window.alert(data);
                location.reload();
            },
            type: 'PATCH',
            url: `${url}/doacao/apagar/${valor}`
        });
    }
}
