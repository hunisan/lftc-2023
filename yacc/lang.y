%{
    #include<stdio.h>
%}
/* Declarations */
%token MAIN
%token VAR
%token IDENTIFIER
%token CONSTANT
%token Integer
%token IF
%token DO
%token ELSE
%token END
%token FOR
%token WHILE
%token AND
%token OR
%token XOR
%token READ
%token WRITE



%%
/* Rules */
program: declaration_list MAIN statement_list
        ;
statement_list: statement
        | statement statement_list
        ;
declaration_list: declaration
        | declaration declaration_list
        ;
statement: simple_statement
        | structural_statement
        ;
simple_statement: declaration
        | assignment
        | io_statement
        ;
declaration: VAR IDENTIFIER ':' type
        ;
type: Integer
        ;
assignment: IDENTIFIER '<-' expression
        ;
io_statement: read_statement
        | write_statement
        ;
read_statement: READ IDENTIFIER
        ;
write_statement: WRITE expression
        ;
expression: term math_operator1 expression
        | term
        ;
term: factor math_operator2 term
        | factor
        ;
factor: IDENTIFIER
        | CONSTANT
        ;
math_operator1: '+'
        | '-'
        ;
math_operator2: '/'
        | '*'
        ;
structural_statement: if_statement
        | for_statement
        | while_statement
        ;
if_statement: simple_if_statement END
        | simple_if_statement else_statement END
        ;
simple_if_statement: IF condition DO statement_list
        ;
else_statement: ELSE DO statement_list
        ;
for_statement: FOR IDENTIFIER '=' expression ',' expression ',' expression DO statement_list END
        ;
while_statement: WHILE condition DO statement_list END
        ;
condition: logical_expression
        | logical_expression logical_operator condition
        ;
logical_expression: expression
        | expression relation logical_expression
        ;
relation: '<'
        | '='
        | '>'
        ;
logical_operator: AND
        | OR
        | XOR
        ;
%%

/* Code */  
extern FILE *yyin;

main(int argc, char **argv)
{    
    if(argc>1) yyin = fopen(argv[1], "r");

    if(!yyparse()) fprintf(stderr,"\tO.K.\n");
}

yyerror(s)
char *s;
{
    fprintf(stderr, "%s\n",s);
}

yywrap()
{
    return(1);
}