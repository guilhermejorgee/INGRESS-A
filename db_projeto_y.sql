create database db_projeto_y;

use db_projeto_y;

create table tb_usuario(
id bigint primary key auto_increment,
nome varchar(100) not null,
email varchar(100) not null,
senha varchar(100) not null,
usuarioEmpregador boolean,
descSobre varchar(255)
);

select * from tb_usuario;
insert tb_usuario (nome, email, senha, usuarioEmpregador, descSobre) values
("Gonzales Walisson","gonzgonz@green.com", "*******", 1, "Eu tanana... tanana, gosto de trabalhar em equipe .... tanana ...");

create table tb_tema(
id bigint primary key auto_increment,
categoria varchar(100) not null,
cargo varchar(100) not null,
remoto varchar(100) not null
);
select * from tb_tema;
insert  tb_tema  (categoria, cargo, remoto) values
("Área de TI", "Programador Jr",1),   ("Financeira",  "contador",1), ("Audiovisual","Operador de camera", 0),
("Área de TI", "Programador Jr",0),   ("Financeira",  "contador",0), ("Policia", "Militar",0),
("Área de TI", "Scrum Master", 1);



create table tb_postagem(
id bigint primary key auto_increment,
tipoDePostagem boolean,
dataPostagem date,
titulo varchar(100) not null,
texto text not null,
qtCurtidas bigint,
tema_id bigint,
usuario_id bigint,
foreign key (tema_id) references tb_tema (id),
foreign key (usuario_id) references tb_usuario (id)
);

insert tb_postagem (tipoDePostagem, dataPostagem, titulo, texto, qtCurtidas, tema_id, usuario_id) values
(2, "2021-05-24", "Procuro Emprego na Área X", "Eu ... isso e aquilo, me da essa vaga pfvr,, amo nao morrer de fome... cansei de ser pobre >>", 666, 1, 1 );