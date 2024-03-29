# Internship Manager - Gerador de Documentação para Estagiários

O Internship Manager é um sistema capaz de gerenciar documentos de alunos em estágios, evitando a digitação de vários documentos.

## Requisitos

> Leia o [FOR-WINDOWS.md](FOR-WINDOWS.md) para versão Windows com Instalador.

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
 
## Instalação comum

1) Instale o servidor de banco de dados PostgreSQL. Crie um usuário `postgres` com senha `postgres`.

2) Instale o `Java 8 SE` ou versão superior e execute o arquivo `jar`.

3) A pasta `backup` terá um snapshot do  banco a cada 5 dias. Use algum app de backup para ficar sincronizando esse diretório.

## Instalação via Docker

```bash
# MODE 1: Easy way
sudo docker run -d --name box1 -p 8080:8080 ailtonbsj/internship-manager

# MODE 2: Persisting database in a volume
sudo docker volume create IMPostgres
sudo docker run -d --name box1 -p 8080:8080 --mount source=IMPostgres,target=/var/lib/postgresql/14/ ailtonbsj/internship-manager

# MODE 3: Exposing Document folder
mkdir ~/Publico
sudo docker run -d --name box1 -p 8080:8080 -v ~/Publico:/opt/webswing/apps/internshipmanager/documentos ailtonbsj/internship-manager

# MODE 4: Exposing Backup folder
mkdir ~/Publico
sudo docker run -d --name box1 -p 8080:8080 -v ~/Publico:/opt/webswing/apps/internshipmanager/backups ailtonbsj/internship-manager

# FULL COMPLETE MODE
mkdir ~/Publico
sudo docker volume create IMPostgres
sudo docker run -d --name box1 -p 8080:8080 --mount source=IMPostgres,target=/var/lib/postgresql/14/ -v ~/Publico:/opt/webswing/apps/internshipmanager/documentos -v ~/Publico:/opt/webswing/apps/internshipmanager/backups ailtonbsj/internship-manager
```

## Gerando uma Imagem Docker com Webswing e PostgreeSQL

Execute o script `build-docker-image.sh` para gerar um imagem docker pronta.

## Telas

![Tela 1](https://a.fsdn.com/con/app/proj/internshipmanager/screenshots/1.png)


![Tela 2](https://a.fsdn.com/con/app/proj/internshipmanager/screenshots/_2.png)


![Tela 3](https://a.fsdn.com/con/app/proj/internshipmanager/screenshots/3.png)

## Campos dinâmicos para modelos

Campos aos quais você pode adicionar em um modelo de documento Word 2007 (.docx) dentro da pasta `modelos`.

```
#ORIENTADOR# #CNPJ# #MATRICULA# #DATA_INICIO# #DATA_FIM# #ATIVALUNO# #DATA_CADASTRO#
#SETOR_ESTAGIO# #HORARIO# #EMPRESA# #NOME_FANTASIA# #ENDEMPRESA# #BAIRROEMPRESA#
#CIDADEEMPRESA# #UFEMPRESA# #CEPEMPRESA# #EMAILEMPRESA# #TELEFONEEMPRESA# #RAMO#
#ATIVEMPRESA# #SEMESTRE# #CPF# #CURSO# #RG# #ALUNO# #NASCIMENTO# #ENDALUNO#
#BAIRROALUNO# #CIDADEALUNO# #EMAILALUNO# #CEPALUNO# #TELEFONEALUNO# #CELULARALUNO#
#MAE# #PAI# #UFALUNO#
```

## Links

 - [Apache POI](https://poi.apache.org/)
