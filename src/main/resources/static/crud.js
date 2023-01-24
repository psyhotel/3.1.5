$(document).ready(function () {
    $('#editUserDlg').on('show.bs.modal', function (event) {
        var dlg = $(this);
        var user_id = $(event.relatedTarget).data('id');

        dlg.find('form').trigger('reset');

        $.ajax({
            url: '/admin/users/' + user_id,
            method: 'get',
            dataType: 'json'
        })
            .done(function (user) {
                dlg.find('#editid').val(user.id);
                dlg.find('#editusername').val(user.username);
                dlg.find('#editlastname').val(user.surname);
                dlg.find('#editage').val(user.age);
                dlg.find('#editemail').val(user.email);
                dlg.find('#editpassword').val(user.password);

                dlg.find('#roleSet option').each(function (role) {
                    var option = $(this);
                    var role_id = parseInt(option.attr('value'));

                    option.prop('selected', false);

                    for (role of user.roleSet) {
                        if (role.id == role_id) {
                            option.prop('selected', true);
                        }
                    }
                });
                console.log(user);
            });
    });

    $('#editUserDlg .btn-save').on('click', function () {
        var dlg = $(this).parents("#editUserDlg");

        $.ajax({
            url: '/admin/update',
            method: 'post',
            dataType: 'json',
            data: {
                "id": dlg.find('#editid').val(),
                "name": dlg.find('#editusername').val(),
                "surname": dlg.find('#editlastname').val(),
                "age": dlg.find('#editage').val(),
                "email": dlg.find('#editemail').val(),
                "password": dlg.find('#editpassword').val(),

                "roleSet": dlg.find('#roleSet').val().toString()
            }
        })
            .done(function (user) {
                var tr = $('#usersList').find('td[scope="row"]:contains("' + user.id + '")').parent('tr');
                tr.children('td').each(function (i, el) {
                    var td = $(el);
                    switch (i) {

                        case 1: {
                            td.text(user.username);
                            break;
                        }
                        case 2: {
                            td.text(user.surname);
                            break;
                        }
                        case 3: {
                            td.text(user.age);
                            break;
                        }
                        case 4: {
                            td.text(user.email);
                            break;
                        }
                        case 5: {
                            var html = '';
                            for (var prop in user.roleSet) {
                                html += '<span class="mr-1">' + user.roleSet[prop].role + '</span>';
                            }
                            td.html(html);
                            break;
                        }
                    }
                });
                console.log(user);
            });
        dlg.modal('hide');
    });

    $('#deleteUserDlg').on('show.bs.modal', function (event) {
        var dlg = $(this);
        var user_id = $(event.relatedTarget).data('id');

        dlg.find('form').trigger('reset');

        $.ajax({
            url: '/admin/users/' + user_id,
            method: 'get',
            dataType: 'json'
        })
            .done(function (user) {
                dlg.find('#deleteid').val(user.id);
                dlg.find('#deleteusername').val(user.name);
                dlg.find('#deletelastname').val(user.surname);
                dlg.find('#deleteage').val(user.age);
                dlg.find('#deleteemail').val(user.email);
                dlg.find('#deletepassword').val(user.password);
                dlg.find('#deleteroleSet option').each(function (role) {
                    var option = $(this);
                    var role_id = parseInt(option.attr('value'));
                    option.prop('selected', false);
                    for (role of user.roleSet) {
                        if (role.id == role_id) {
                            option.prop('selected', true);
                        }
                    }
                });
                console.log(user);
            });
    });

    $('#deleteUserDlg').on('click', '.btn-delete', function (event) {
        var dlg = $(this).parents('#deleteUserDlg');
        var user_id = dlg.find('#deleteid').val();
        var tr = $('#usersList').find('td[scope="row"]:contains("' + user_id + '")').parent('tr');

        $.ajax({
            url: '/admin/delete/' + user_id,
            method: 'post',
            dataType: 'json'
        })

            .done(function (access) {
                tr.remove();
            })
        dlg.modal('hide');
    })

    $('#saveUserCard .btn-save').on('click', function (event) {
        event.preventDefault();
        var card = $(this).parents('#saveUserCard');

        $.ajax({
            url: '/admin/add',
            method: 'POST',
            dataType: 'json',
            data: {
                "id": card.find('#addid').val(),
                "name": card.find('#addusername').val(),
                "surname": card.find('#addlastname').val(),
                "age": card.find('#addage').val(),
                "email": card.find('#addemail').val(),
                "password": card.find('#addpassword').val(),
                "roleSet": card.find('#addroles').val().toString()
            }
        })
            .done(function (user) {
                card.find('form').trigger('reset');
                var roleStr = '';
                for (var i = 0; i < user.roleSet.length; i++) {
                    roleStr += '<span class="mr-1">' + user.roleSet[i].role + '</span>';
                }
                $('<tr>' +
                    '<td scope="row">' + user.id + '</td>' +
                    '<td>' + user.username + '</td>' +
                    '<td>' + user.surname + '</td>' +
                    '<td>' + user.age + '</td>' +
                    '<td>' + user.email + '</td>' +
                    '<td>' + roleStr + '</td>' +
                    '<td>' +
                    '   <a href="#" class="btn btn-primary" data-toggle="modal" data-target="#editUserDlg" data-id="' + user.id + '" role="button">Edit</a>' +
                    '   <a href="#" class="btn btn-danger ml-1 btn-delete" data-toggle="modal" data-target="#deleteUserDlg" data-id="' + user.id + '" role="button">Delete</a>' +
                    '</td>' +
                    '</tr>'
                ).appendTo('#usersList tbody');
                $('#users-list-tab').tab('show');
            });
    });
})