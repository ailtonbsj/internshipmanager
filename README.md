# Internship Manager - Gerador de Documentação para Estagiários

O Internship Manager é um sistema capaz gerenciar documentos de alunos em estágios, evitando a digitação de vários documentos.

## REQUISITOS
 - Sistema Windows 7 ou superior
 - Java SE 1.7
 - Sevidor PostgreSQL 9.3

## CARACTERISTICAS
 - Capacidade de Adicionar, Remover ou Atualizar Orientadores de Estágios
 - Adição, Remoção e Atualização dos Cursos da Instituição
 - Gerenciamento dos Estagiários
 - Gerenciamento das Empresas que fornecem Estágios
 - Adição, Remoção e Atualização do Aluno em determinada Empresa
 - Geração de Documentação para o aluno mensalmente (Frequência, Comprovante de Matrícula, Termo de Compromisso e outros)
 - Capacidade de Adicionar novos modelos para Documentação do aluno
 - Backup de banco de dados automatizado mensalmente
 - Backup na Nuvem automatizado caso Google Drive instalado
 
## INSTALAÇÃO
1) Instale o servidor de banco de dados PostgreSQL 9.3. No momento da instalação forneca a senha 123 para o administrador root.

2) Instale o Google Driver.

3) Instale o InternshipManagerSetup.exe. [Clique aqui para baixar](#Executavel InternshipManagerSetup).

4) Usando o prompt de comando execute:

	mklink /D "%USERPROFILE%\Google Drive\BKP Internship"  "C:\InternshipManager1\backups"

## Links

 - [Apache POI] (https://poi.apache.org/)
 - [Executavel InternshipManagerSetup] (https://sites.google.com/site/ailtonproject/InternshipManagerSetup.zip)