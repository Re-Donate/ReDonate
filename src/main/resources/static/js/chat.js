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

function selectUser(doacaoId, connectNome, connectId) {
    if(selectedChat != doacaoId) {
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
