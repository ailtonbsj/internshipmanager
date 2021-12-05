# Internship Manager - Gerador de Documentação para Estagiários

O Internship Manager é um sistema capaz de gerenciar documentos de alunos em estágios, evitando a digitação de vários documentos.

## Requisitos

> Leia o [FOR-WINDOWS.md](README.md) para versão Windows com Instalador.

 - Java SE 1.8
 - Servidor PostgreSQL

## Caracteristicas

 - Capacidade de Adicionar, Remover ou Atualizar Orientadores de Estágios
 - Adição, Remoção e Atualização dos Cursos da Instituição
 - Gerenciamento dos Estagiários
 - Gerenciamento das Empresas que fornecem Estágios
 - Adição, Remoção e Atualização do Aluno em determinada Empresa
 - Geração de documentação para o aluno mensalmente (Frequência, Comprovante de Matrícula, Termo de Compromisso e outros)
 - Capacidade de Adicionar novos modelos para documentação do aluno
 - Backup de banco de dados automatizado mensalmente
 
## Instalação

1) Instale o servidor de banco de dados PostgreSQL. Crie um usuário `postgres` com senha `postgres`.

2) Instale o `Java 8 SE` ou versão superior e execute o arquivo `jar`.

3) A pasta `backup` terá um snapshot do  banco a cada 5 dias. Use algum app de backup para ficar sincronizando esse diretório.

## Telas

![Tela 1](https://a.fsdn.com/con/app/proj/internshipmanager/screenshots/1.png)


![Tela 2](https://a.fsdn.com/con/app/proj/internshipmanager/screenshots/_2.png)


![Tela 3](https://a.fsdn.com/con/app/proj/internshipmanager/screenshots/3.png)

## Links

 - [Apache POI](https://poi.apache.org/)
