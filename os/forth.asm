#macros
.include "macros.asm"

.macro stack_check ($num)
	la $t1, fp_stack
	subu $t2, $t1, $num
	bgtu $fp, $t2, stack_empty
.end_macro

#data
.data			
		.space 256
fp_stack: 	.word 0
		.space 256
sp_stack: 	.word 0

#messages
unknow_msg: 		.byte 14 
			.asciiz "Unknow Token: "
ok_msg: 		.byte 2 
			.asciiz "ok"
stack_empty_msg: 	.byte 11
			.asciiz "Stack Empty"
stack_overflow_msg: 	.byte 14
			.asciiz "Stack Overflow"
start_message:		.byte 19
			.asciiz "RED OS Initialized."
crash_message:		.byte 20
			.asciiz "Unexpected Exception"
			
tmp_buf:	.space 128
output_buf:	.space 128
word_buf:	.space 80

		.align 2
var_pointer:	.word 0
var_compilee:	.word 0
var_csp: 	.word 0
var_fence: 	.word init_here
var_r0:		.word fp_stack
var_s0:		.word sp_stack
var_scr: 	.word 0
var_if: 	.word 0
p_run_end:	.word c_run_end
p_run_program:	.word c_run_program

d_forth:	.byte	5
		.ascii	"FORTH"
		.align	2
		.word	d_set
p_forth:	.word 	c_forth

d_set:		.byte 1
		.ascii "!"
		.align 2
		.word d_at
p_set:		.word c_set

d_at:		.byte 1
		.ascii "@"
		.align 2
		.word d_plus
p_at:		.word c_at

d_plus:		.byte 1
		.ascii "+"
		.align 2
		.word d_minus
p_plus:		.word c_plus
		
d_minus:	.byte 1
		.ascii "-"
		.align 2
		.word d_star
p_minus:	.word c_minus

d_star:		.byte 1
		.ascii "*"
		.align 2
		.word d_slash
p_star:		.word c_star

d_slash:	.byte 1
		.ascii "/"
		.align 2
		.word d_slash_mod
p_slash:	.word c_slash

d_slash_mod:	.byte 4
		.ascii "/MOD"
		.align 2
		.word d_star_slash
p_slash_mod:	.word c_slash_mod

d_star_slash:	.byte 2
		.ascii "*/"
		.align 2
		.word d_star_slash_mod
p_star_slash:	.word c_star_slash

d_star_slash_mod:.byte 5
		.ascii "*/MOD"
		.align 2
		.word d_plus_set
p_star_slash_mod:.word c_star_slash_mod

d_plus_set:	.byte 2
		.ascii "+!"
		.align 2
		.word d_zero_less
p_plus_set:	.word c_plus_set

d_zero_less:	.byte 2
		.ascii "0<"
		.align 2
		.word d_zero_equal
p_zero_less:	.word c_zero_less

d_zero_equal:	.byte 2
		.ascii "0="
		.align 2
		.word d_zero_more
p_zero_equal:	.word c_zero_equal

d_zero_more:	.byte 2
		.ascii "0>"
		.align 2
		.word d_one_plus
p_zero_more:	.word c_zero_more

d_one_plus:	.byte 2
		.ascii "1+"
		.align 2
		.word d_dot
p_one_plus:	.word c_one_plus

d_dot:		.byte 1
		.ascii "."
		.align 2
		.word d_dot_s
p_dot:		.word c_dot

d_dot_s:	.byte 2
		.ascii ".S"
		.align 2
		.word d_one_minus
p_dot_s:	.word c_dot_s

d_one_minus:	.byte 2
		.ascii "1-"
		.align 2
		.word d_two_plus
p_one_minus:	.word c_one_minus

d_two_plus:	.byte 2
		.ascii "2+"
		.align 2
		.word d_two_minus
p_two_plus:	.word c_two_plus

d_two_minus:	.byte 2
		.ascii "2-"
		.align 2
		.word d_two_slash
p_two_minus:	.word c_two_minus

d_two_slash:	.byte 2
		.ascii "2/"
		.align 2
		.word d_less
p_two_slash:	.word c_two_slash

d_less:		.byte 1
		.ascii "<"
		.align 2
		.word d_equal
p_less:		.word c_less

d_equal:	.byte 1
		.ascii "="
		.align 2
		.word d_more
p_equal:	.word c_equal

d_more:		.byte 1
		.ascii ">"
		.align 2
		.word d_more_r
p_more:		.word c_more

d_more_r:	.byte 2
		.ascii ">R"
		.align 2
		.word d_q_dup
p_more_r:	.word c_more_r

d_q_dup:	.byte 4
		.ascii "?DUP"
		.align 2
		.word d_abs
p_q_dup:	.word c_q_dup

d_abs:		.byte 3
		.ascii "ABS"
		.align 2
		.word d_and
p_abs:		.word c_abs

d_and:		.byte 3
		.ascii "AND"
		.align 2
		.word d_c_set
p_and:		.word c_and

d_c_set:	.byte 2
		.ascii "C!"
		.align 2
		.word d_c_at
p_c_set:	.word c_c_set

d_c_at:		.byte 2
		.ascii "C@"
		.align 2
		.word d_cmove
p_c_at:		.word c_c_at

d_cmove:	.byte 5
		.ascii "CMOVE"
		.align 2
		.word d_cmove_more
p_cmove:	.word c_cmove

d_cmove_more:	.byte 6
		.ascii "CMOVE>"
		.align 2
		.word d_count
p_cmove_more:	.word c_cmove_more

d_count:	.byte 5
		.ascii "COUNT"
		.align 2
		.word d_words
p_count:	.word c_count

#Double Int operations not implemented

d_words:	.byte 5
		.ascii "WORDS"
		.align 2
		.word d_depth
p_words:	.word c_words

d_depth:	.byte 5
		.ascii "DEPTH"
		.align 2
		.word d_drop
p_depth:	.word c_depth

d_drop:		.byte 4
		.ascii "DROP"
		.align 2
		.word d_dup
p_drop: 	.word c_drop

d_dup:		.byte 3
		.ascii "DUP"
		.align 2
		.word d_execute
p_dup:	 	.word c_dup

d_execute:	.byte 7
		.ascii "EXECUTE"
		.align 2
		.word d_exit
p_execute:	.word c_execute

d_exit:		.byte 4
		.ascii "EXIT"
		.align 2
		.word d_fill
p_exit:		.word c_exit

d_fill:		.byte 4
		.ascii "FILL"
		.align 2
		.word d_i
p_fill:		.word c_fill

d_i:		.byte 1
		.ascii "I"
		.align 2
		.word d_j
p_i:		.word c_i

d_j:		.byte 1
		.ascii "J"
		.align 2
		.word d_max
p_j:		.word c_j

d_max:		.byte 3
		.ascii "MAX"
		.align 2
		.word d_min
p_max:		.word c_max

d_min:		.byte 3
		.ascii "MIN"
		.align 2
		.word d_mod
p_min:		.word c_min

d_mod:		.byte 3
		.ascii "MOD"
		.align 2
		.word d_negate
p_mod:		.word c_mod

d_negate:	.byte 6
		.ascii "NEGATE"
		.align 2
		.word d_not
p_negate:	.word c_negate

d_not:		.byte 3
		.ascii "NOT"
		.align 2
		.word d_or
p_not:		.word c_not

d_or:		.byte 2
		.ascii "OR"
		.align 2
		.word d_over
p_or:		.word c_or

d_over:		.byte 4
		.ascii "OVER"
		.align 2
		.word d_pick
p_over:		.word c_over

d_pick:		.byte 4
		.ascii "PICK"
		.align 2
		.word d_r_more
p_pick:		.word c_pick

d_r_more:	.byte 2
		.ascii "R>"
		.align 2
		.word d_r_at
p_r_more:	.word c_r_more

d_r_at:		.byte 2
		.ascii "R@"
		.align 2
		.word d_roll
p_r_at:		.word c_r_at

d_roll:		.byte 4
		.ascii "ROLL"
		.align 2
		.word d_rot
p_roll:		.word c_roll

d_rot:		.byte 3
		.ascii "ROT"
		.align 2
		.word d_swap
p_rot:		.word c_rot

d_swap:		.byte 4
		.ascii "SWAP"
		.align 2
		.word d_u_less
p_swap:		.word c_swap

d_u_less:	.byte 2
		.ascii "U<"
		.align 2
		.word d_um_star
p_u_less:	.word c_u_less

d_um_star:	.byte 3
		.ascii "UM*"
		.align 2
		.word d_um_slash_mod
p_um_star:	.word c_um_star

d_um_slash_mod:	.byte 6
		.ascii "UM/MOD"
		.align 2
		.word d_xor
p_um_slash_mod:	.word c_um_slash_mod

d_xor:		.byte 3
		.ascii "XOR"
		.align 2
		.word d_not_equal
p_xor:		.word c_xor

d_not_equal:	.byte 2
		.ascii "<>"
		.align 2
		.word d_zero_set
p_not_equal:	.word c_not_equal

d_zero_set:	.byte 2
		.ascii "0!"
		.align 2
		.word d_if
p_zero_set:	.word c_zero_set

d_if:		.byte 0x82 # INMEDIATE
		.ascii "IF" #FIX
		.align 2
		.word d_then
p_if:		.word c_if

d_then:		.byte 0x84 # INMEDIATE
		.ascii "THEN" #FIX
		.align 2
		.word d_else
p_then:		.word c_then

d_else:		.byte 0x84 # INMEDIATE
		.ascii "ELSE" #FIX
		.align 2
		.word d_lit
p_else:		.word c_else

d_lit:		.byte 3
		.ascii "LIT"
		.align 2
		.word d_begin
p_lit:		.word c_lit

d_begin:	.byte 5
		.ascii "BEGIN" #FIX
		.align 2
		.word d_again
p_begin:	.word c_begin

d_again:	.byte 5
		.ascii "AGAIN" #FIX
		.align 2
		.word d_comma
p_again:	.word c_again

d_comma:	.byte 1
		.ascii ","
		.align 2
		.word d_cor_compile
p_comma:	.word c_comma

d_cor_compile:	.byte 9
		.ascii "[COMPILE]" #TODO
		.align 2
		.word d_literal
p_cor_compile:	.word c_cor_compile

d_literal:	.byte 0x87 # INMEDIATE
		.ascii "LITERAL"
		.align 2
		.word d_q_stack
p_literal:	.word c_literal

d_q_stack:	.byte 6
		.ascii "?STACK" #TODO
		.align 2
		.word d_int_expect
p_q_stack:	.word c_q_stack

d_int_expect:	.byte 8
		.ascii "(EXPECT)"
		.align 2
		.word d_type
p_int_expect:	.word c_int_expect

d_type:		.byte 4
		.ascii "TYPE"
		.align 2
		.word d_space
p_type:		.word c_type

d_space:	.byte 5
		.ascii "SPACE"
		.align 2
		.word d_allot
p_space:	.word c_space

d_allot:	.byte 5
		.ascii "ALLOT"
		.align 2
		.word d_dp_set
p_allot:	.word c_allot

d_dp_set:	.byte 3
		.ascii "DP!"
		.align 2
		.word d_align
p_dp_set:	.word c_dp_set

d_align:	.byte 5
		.ascii "ALIGN"
		.align 2
		.word d_alignw
p_align:	.word c_align

d_alignw:	.byte 6
		.ascii "ALIGNW"
		.align 2
		.word d_alignh
p_alignw:	.word c_alignw

d_alignh:	.byte 6
		.ascii "ALIGNH"
		.align 2
		.word d_two_dup
p_alignh:	.word c_alignh

d_two_dup:	.byte 4
		.ascii "2DUP"
		.align 2
		.word d_enclose
p_two_dup:	.word c_two_dup

d_enclose:	.byte 7
		.ascii "ENCLOSE"
		.align 2
		.word d_branch
p_enclose:	.word c_enclose

d_branch:	.byte 6
		.ascii "BRANCH" # TODO remplace this for ?BRANCH
		.align 2
		.word d_int_else
p_branch:	.word c_branch

d_int_else:	.byte 6
		.ascii "(ELSE)" # TODO replace this for banch
		.align 2
		.word d_bracket_1
p_int_else:	.word c_int_else

d_bracket_1:	.byte 1
		.ascii "["
		.align 2
		.word d_bracket_2
p_bracket_1:	.word c_bracket_1

d_bracket_2:	.byte 1
		.ascii "]"
		.align 2
		.word d_cr
p_bracket_2:	.word c_bracket_2

d_cr:		.byte 2
		.ascii "CR"
		.align 2
		.word d_page
p_cr:		.word c_cr

d_page:		.byte 4
		.ascii "PAGE"
		.align 2
		.word d_free
p_page:		.word c_page

d_free:		.byte 4
		.ascii "FREE"
		.align 2
		.word d_hex
p_free:		.word c_free

d_hex:		.byte 3
		.ascii "HEX"
		.align 2
		.word d_dec
p_hex:		.word c_hex

d_dec:		.byte 7
		.ascii "DECIMAL"
		.align 2
		.word d_paren
p_dec:		.word c_dec

d_paren:	.byte 0x81 # INMEDIATE
		.ascii "("
		.align 2
		.word d_inv_paren
p_paren:	.word c_paren

d_inv_paren:	.byte 0x81 # INMEDIATE
		.ascii ")"
		.align 2
		.word d_dot_quote
p_inv_paren:	.word c_inv_paren

d_dot_quote:	.byte 0x82 # INMEDIATE
		.ascii ".\""
		.align 2
		.word d_quote
p_dot_quote:	.word c_dot_quote

d_quote:	.byte 0x81 # INMEDIATE
		.ascii "\""
		.align 2
		.word d_colon
p_quote:	.word c_quote

d_colon:	.byte 0x81 # INMEDIATE
		.ascii ":"
		.align 2
		.word d_semi_colon
p_colon:	.word c_colon

d_semi_colon:	.byte 0x81 # INMEDIATE
		.ascii ";"
		.align 2
		.word d_create
p_semi_colon:	.word c_semi_colon

d_create:	.byte 6
		.ascii "CREATE"
		.align 2
		.word d_variable
p_create:	.word c_create

d_variable:	.byte 8
		.ascii "VARIABLE"
		.align 2
		.word d_constant
p_variable:	.word c_variable

d_constant:	.byte 8
		.ascii "CONSTANT"
		.align 2
		.word d_query
p_constant:	.word c_constant

# high level words

# : QUERY ( -- )
d_query:	.byte 5
		.ascii "QUERY"
		.align	2
		.word d_interpret
p_query:	.word c_query
		
# INTERPRET ( -- )
d_interpret:	.byte 9
		.ascii "INTERPRET"
		.align	2
		.word d_expect
p_interpret:	.word c_interpret
		
# EXPECT  ( addr, n -- )	
d_expect:	.byte 6
		.ascii "EXPECT"
		.align	2
		.word d_minus_find
p_expect:	.word c_expect
		
# -FIND ( -- addr n1 bool) or (-- bool)	
d_minus_find:	.byte 5
		.ascii "-FIND"
		.align	2
		.word d_word
p_minus_find:	.word c_minus_find

# WORD   ( char -- addr )   
d_word:		.byte 4
		.ascii "WORD"
		.align 2
		.word d_find
p_word:		.word c_word

# FIND  ( addr1 -- addr2, flag)
d_find:		.byte 4
		.ascii "FIND"
		.align 2
		.word d_int_find
p_find:		.word c_find

# (FIND) (addr1 addr2 -- n3 n4 true) or (addr1 addr2 -- false)
d_int_find:	.byte 6
		.ascii "(FIND)"
		.align 2
		.word d_number
p_int_find:	.word c_int_find

# : NUMBER ? TODO
d_number:	.byte 6
		.ascii "NUMBER"
		.align 2
		.word d_tib_var
p_number:	.word c_number
		
#Vars
		
# input buffer TIB
d_tib_var:	.byte 3
		.ascii "TIB"
		.align 2
		.word d_more_in #link
p_tib:		.word read_var	
var_tib:	.space 80 	#input text buffer

# >IN char offset
d_more_in:	.byte 3
		.ascii ">IN"
		.align 2
		.word d_span #link
p_more_in:	.word read_var	
var_more_in:	.word 0

# SPAN
d_span:		.byte 4
		.ascii "SPAN"
		.align 2
		.word d_hash_tib #link
p_span:		.word read_var	
var_span:	.word 0
		
# #TIB
d_hash_tib:	.byte 4
		.ascii "#TIB"
		.align 2
		.word d_blk #link
p_hash_tib:	.word read_var	
var_hash_tib:	.word 0

# BLK block used to get the input for the intepreter, 0 means TIB
d_blk:		.byte 3
		.ascii "BLK"
		.align 2
		.word d_state #link
p_blk:		.word read_var	
var_blk:	.word 0

# STATE compiling?
d_state:	.byte 5
		.ascii "STATE"
		.align 2
		.word d_bl #link
p_state:	.word read_var	
var_state:	.word 0

# BL space char
d_bl:		.byte 2
		.ascii "BL"
		.align 2
		.word d_dpl #link
p_bl:		.word read_var	
var_bl:		.word 32 # caracter espacio

# DPL posistion of the decimal point in a number, not used, no decimals supported
d_dpl:		.byte 3
		.ascii "DPL"
		.align 2
		.word d_here #link
p_dpl:		.word read_var	
var_dpl:	.word 0

# HERE position here the free memory starts
d_here:		.byte 4
		.ascii "HERE"
		.align 2
		.word d_block #link
p_here:		.word read_var	
var_here:	.word init_here

# BLOCK not used yet
d_block:	.byte 5
		.ascii "BLOCK"
		.align 2
		.word d_current #link
p_block:	.word read_var	
var_block:	.word 0

# CURRENT not used
d_current:	.byte 7
		.ascii "CURRENT"
		.align 2
		.word d_context #link
p_current:	.word read_var	
var_current:	.word 0

# CONTEXT first word in the dict
d_context:	.byte 7
		.ascii "CONTEXT"
		.align 2
		.word d_base #link
p_context:	.word read_var	
var_context:	.word d_forth

# BASE base of the numbers displayed or readed
d_base:		.byte 4
		.ascii "BASE"
		.align 2
		.word d_cell #link
p_base:		.word read_var
var_base:	.word 10

# CELL number of bytes in a word
d_cell:		.byte 4
		.ascii "CELL"
		.align 2
		.word 0 #link
p_cell:		.word read_const
var_cell:	.word 4

init_here:
		.space 128 #65536
.text
#main
main: 	
	la $fp, fp_stack
	la $sp, sp_stack
	jal clearScreen
	la $t0, start_message
	push_fp $t0
	jal print_string
	
	j c_forth
	
# High Level Words
# FORTH ( ->) BEGIN QUERY INTERPRET  AGAIN ;	
c_forth:
	#jump line
	li $a0, 13
	jal print_char
	#print '>'
	li $a0, 62
	jal print_char
	#print space
	lb $a0, var_bl
	jal print_char
	
	jal c_query
	jal c_interpret
	
	j c_forth

#QUERY ( -- ) ING: fills the TIB buffer with the user imput, also clear some vars: >IN, BLK, #TIB, SPAN
#Esp: llena el buffer tib con el imput del usuario y hace un clear de varibles
c_query:
	push $ra
	la $t0, var_tib
	push_fp $t0
	li $t0, 80
	push_fp $t0
	jal c_expect
	sw $zero, var_more_in
	sw $zero, var_blk
	lw $t0, var_span
	sw $t0, var_hash_tib
	next
	
# INTERPRET ( -- ) 
c_interpret:
	jal c_minus_find # ( -- addr flag) or (-- addr false)
	pop_fp $t0	
	beqz $t0, c_interpret_else
	jal c_execute
	j c_interpret_exit
c_interpret_else:
	jal c_number
c_interpret_exit:
	jal c_q_stack
	j c_interpret
	
# : colon compiler
c_colon:
	push $ra
	jal c_create
	pop_fp $t0
	lw $t1, p_run_program
	sw $t1, ($t0)
	
	jal c_bracket_2
c_colon_begin:
	jal c_minus_find # ( -- addr flag) or (-- addr false)
	pop_fp $t0	
	beqz $t0, c_colon_else
	addi $t0, $t0, 1
	beqz $t0, c_colon_no_inmediate
	jal c_execute
	j c_colon_exit
c_colon_no_inmediate:
	lw $t0, var_state
	bnez $t0, c_colon_compile
	jal c_execute
	j c_colon_exit
c_colon_compile:
	jal c_comma
	j c_colon_exit
c_colon_else:
	jal c_number
	jal c_literal
c_colon_exit:
	jal c_q_stack
	j c_colon_begin
	
# ; end of a word definition
c_semi_colon:
	la $t0, p_run_end
	pop_fp $t0
	jal c_comma
	jal c_bracket_1
	next
	
# CREATE (-- addr) creates a dictionary entry and return the body address
c_create:
	push $ra
	lw $t0, var_bl
	push_fp $t0
	jal c_word
	pop_fp $t0
	lb $t1, ($t0)
	beqz $t1, c_create_exit
	push $t0
	la $t0, d_forth
c_create_next_word:
	lbu $a0, 0($t0)
	andi $a0, 31
	addu $t0,$t0, 1
	addu $t0, $t0, $a0
	addu $t0,$t0, 3
	andi $t0,$t0, 0xfffffffc
	move $t5, $t0
	lw $t0, 0($t0)
	bnez $t0, c_create_next_word
	lw $t1, var_here
	sw $t1, ($t5) # stores in t0 (last dict entry with pointer to 0) the value of here
	pop $t2 # string from word
	lb $t3, ($t2)
	andi $t3, $t3, 31
	addi $t3, $t3, 1 # add 1 to include the string count
	move $t1, $t2	
	lw $t0, var_here
c_create_loop:	
	beqz $t3, c_create_loop_end
	lb $t4, ($t1)
	sb $t4, ($t0)
	addiu $t1, $t1, 1
	addiu $t0, $t0, 1
	subi $t3, $t3, 1
	j c_create_loop
c_create_loop_end:
	sw $t0, var_here
	jal c_alignw
	lw $t2, var_here
	sw $zero, ($t2)
	addiu $t2, $t2, 4
	push_fp $t0
	sw $t0, ($t2)
	addiu $t2, $t2, 4
	sw $t2, var_here
c_create_exit:
	next
	
# VARIABLE (-- addr)
c_variable:
	push $ra
	lw $t0, var_bl
	push_fp $t0
	jal c_word
	pop_fp $t0
	lb $t1, ($t0)
	beqz $t1, c_variable_exit
	push $t0
	la $t0, d_forth
c_variable_next_word:
	lbu $a0, 0($t0)
	andi $a0, 31
	addu $t0,$t0, 1
	addu $t0, $t0, $a0
	addu $t0,$t0, 3
	andi $t0,$t0, 0xfffffffc
	move $t5, $t0
	lw $t0, 0($t0)
	bnez $t0, c_variable_next_word
	lw $t1, var_here
	sw $t1, ($t5) # stores in t0 (last dict entry with pointer to 0) the value of here
	pop $t2 # string from word
	lb $t3, ($t2)
	andi $t3, $t3, 31
	addi $t3, $t3, 1 # add 1 to include the string count
	move $t1, $t2	
	lw $t0, var_here
c_variable_loop:	
	beqz $t3, c_variable_loop_end
	lb $t4, ($t1)
	sb $t4, ($t0)
	addiu $t1, $t1, 1
	addiu $t0, $t0, 1
	subi $t3, $t3, 1
	j c_variable_loop
c_variable_loop_end:
	sw $t0, var_here
	jal c_alignw
	lw $t2, var_here
	sw $zero, ($t2)
	addiu $t2, $t2, 4
	la $t0, read_var
	sw $t0, ($t2)
	addiu $t2, $t2, 8
	sw $t2, var_here
c_variable_exit:
	next
	
# CONTANT (-- value)
c_constant:
	stack_check 4
	push $ra
	lw $t0, var_bl
	push_fp $t0
	jal c_word
	pop_fp $t0
	lb $t1, ($t0)
	beqz $t1, c_constant_exit
	push $t0
	la $t0, d_forth
c_constant_next_word:
	lbu $a0, 0($t0)
	andi $a0, 31
	addu $t0,$t0, 1
	addu $t0, $t0, $a0
	addu $t0,$t0, 3
	andi $t0,$t0, 0xfffffffc
	move $t5, $t0
	lw $t0, 0($t0)
	bnez $t0, c_constant_next_word
	lw $t1, var_here
	sw $t1, ($t5) # stores in t0 (last dict entry with pointer to 0) the value of here
	pop $t2 # string from word
	lb $t3, ($t2)
	andi $t3, $t3, 31
	addi $t3, $t3, 1 # add 1 to include the string count
	move $t1, $t2	
	lw $t0, var_here
c_constant_loop:	
	beqz $t3, c_constant_loop_end
	lb $t4, ($t1)
	sb $t4, ($t0)
	addiu $t1, $t1, 1
	addiu $t0, $t0, 1
	subi $t3, $t3, 1
	j c_constant_loop
c_constant_loop_end:
	sw $t0, var_here
	jal c_alignw
	lw $t2, var_here
	sw $zero, ($t2)
	addiu $t2, $t2, 4
	la $t0, read_const
	sw $t0, ($t2)
	addiu $t2, $t2, 4
	pop_fp $t5
	sw $t5, ($t2)
	addiu $t2, $t2, 4
	sw $t2, var_here
c_constant_exit:
	next
	
# -FIND ( -- addr flag) or (-- addr false)  ING: makes a tokem from the buffer TIB or BLOCK, and try to find it in the dictionary, if is founded flag will be true
#Esp: coje una WORD del TIB o BLOCK y la busca en el dict, falg true si la encontro, addr es la dir de execucion 
c_minus_find:
	push $ra
	lw $t0, var_bl
	push_fp $t0
	jal c_word
	jal c_find
	next
	
# WORD ((char)n1 -- (String)addr) ING: Reads the buffer TIB or BLOCK until founds the character n1, returns a counted string with all readed chars
#Esp: lee caracters de tib (o block) hasta encontrar el char n1
c_word:
	push $ra	
	lw $t0, var_blk
	beqz $t0, c_word_tib
	la $t1, var_block
	j c_word_then
c_word_tib:
	la $t1, var_tib
c_word_then:
	lw $t2, var_more_in
	addu $t1, $t1, $t2
	lw $t3, var_bl
	li $t6, 0
c_word_trim:
	lb $t2, ($t1)
	bne $t2, $t3, c_word_start
	addiu $t1, $t1, 1
	addi $t6, $t6, 1
	j c_word_trim
c_word_start:
	pop_fp $t0
	la $t3, word_buf
	addiu $t3, $t3, 1
	la $t4, 0
	li $t5, 10
c_word_loop:
	lbu $t2, ($t1)
	sb  $t2, ($t3)
	beq $t2, $t5, c_word_end
	beq $t2, $t0, c_word_end
	beqz $t2, c_word_end
	addiu $t1, $t1, 1
	addiu $t3, $t3, 1
	addi $t4, $t4, 1
	j c_word_loop
c_word_end:
	lw $t2, var_more_in
	add $t2, $t2, $t4
	add $t2, $t2, $t6
	sw $t2, var_more_in
	la $t0, word_buf
	sb $t4, ($t0)
	push_fp $t0
	next
	
# FIND (addr1 -- addr2, flag)
# 
c_find:
	push $ra
	stack_check 4
	pop_fp $t0 # text addr
	la $t1, d_forth
	push_fp $t1
	push_fp $t0
	push $t0
	jal c_int_find
	pop $t0
	pop_fp $t1
	beqz $t1, c_find_error
	pop_fp $t2 # first byte
	andi $t3, $t2, 128
	beqz $t3, c_find_not_inmed
	li $t0, 1
	push_fp $t0
	next
c_find_not_inmed:
	la $t0, -1
	push_fp $t0
	next
c_find_error:
	push_fp $t0
	push_fp $zero
	next
	
# (FIND) (addr1 addr2 -- n3 n4 true) or (addr1 addr2 -- false) busca una entrada en el diccionario
# addr2 is the fist entry in the dict
# addr1 text to search
# n3 = parameter field
# n4 first byte of the text, the lenght and flags
# Search in the dictionary using the string at addr1
c_int_find:
	stack_check 8
	pop_fp $s0 # text
c_int_next_list:
	pop_fp $t0 # dict
	la $a0, -1
	beq $t0, $a0, c_int_find_not_found
c_int_find_check_word:
	lbu $a0, ($s0)
	lbu $a1, ($t0)
	andi $a1, 31
	bne $a0, $a1, c_int_find_next_word
	move $t1, $s0
	move $t2, $t0
	move $t3, $a0
c_int_find_1:
	beqz $t3, c_int_find_found
	addu $t1,$t1, 1
	addu $t2,$t2, 1
	subu $t3,$t3, 1
	lbu $a0, 0($t1)
	lbu $a1, 0($t2)
	beq $a0, $a1, c_int_find_1
c_int_find_next_word:
	lbu $a0, 0($t0)
	andi $a0, 31
	addu $t0,$t0, 1
	addu $t0, $t0, $a0
	addu $t0,$t0, 3
	andi $t0,$t0, 0xfffffffc
	lw $t0, 0($t0)
	bnez $t0, c_int_find_check_word
c_int_find_not_found:
	push_fp $zero
	return
c_int_find_found:
	lbu $v0, 0($t0)
	andi $a0, $v0, 31
	addu $t0,$t0, 1
	addu $t0, $t0, $a0
	addu $t0,$t0, 3
	andi $t0,$t0, 0xfffffffc
	addu $t0,$t0, 4
	move $v1, $t0
	li $a0, 1
	push_fp $v1
	push_fp $v0
	push_fp $a0
	return
	
# EXPECT (addr, n --) 
# Ing: reads the user imput with n max char, saving it in addr
# Esp: lee la entrada del ususario, addr es donde se guardara el string, n es el tamaño maximo
c_expect:
	push $ra
	jal c_dup
	jal c_more_r
	jal c_int_expect
	jal c_dup
	la $t0, var_span
	push_fp $t0
	jal c_set
	# Debug code
	pop_fp $t0
	pop_fp $t0
	#jal c_type 
	#
	jal c_r_more
	la $t0, var_span
	push_fp $t0
	jal c_at
	jal c_minus
	pop_fp $t0
	bnez $t0, c_expect_no_space
	jal c_space
	next
c_expect_no_space:
	next
	
# (EXPECT) (addr n1 -- addr n2)
c_int_expect:
	stack_check 8
	push $ra
	pop_fp $t0 #num
	pop_fp $t1 #addr
	push $t0 #num
	push $t1 #addr
	push_fp $t0 #num
	push_fp $t1 #addr
	
	jal get_raw_input # writes in TIB the user Input
	
	#jal print_tib # debug
	pop $t0 #addr
	pop $t4 #num
	li $t3, 0
	li $t5, 10
c_int_expect_1:
	lb $t1, ($t0)
	beq $t1, $t5, c_int_expect_2
	beqz $t1, c_int_expect_2
	beqz $t4, c_int_expect_2
	addiu $t0 ,$t0, 1 
	addiu $t3 ,$t3, 1
	sub $t4, $t4, 1
	j c_int_expect_1 # for
c_int_expect_2:
	push_fp $t3 # Esp: guarda en el stack la longitud del string
	# ING: save the length of the string in the stack
	next
	
# NUMBER (addr -- n) 
# Convert a string at addr into a number n, using the base
c_number:
	push $ra
	pop_fp $t0 	# addr
	push $t0
	lbu $t1, ($t0) 	# fist byte, string count
	beqz $t1, c_number_end
	lbu $t2, 1($t0) 	# signe
	li $t3, 45
	li $t4, 0
	lw $t5, var_base
	beq $t3, $t2, c_number_negative
	li $t3, 1
	j c_number_loop
c_number_negative:
	la $t3, -1
	addiu $t0, $t0, 1
	subu $t1, $t1, 1
c_number_loop:
	beqz $t1, c_number_return
	lbu $t2, 1($t0)
	subu $t1, $t1, 1
	addiu $t0, $t0, 1
	bltu $t2, 48, c_number_error
	bltu $t2, 58, c_number_nine
	bltu $t2, 65, c_number_error
	subu $t2, $t2, 55
	bgt $t2, $t5, c_number_error
	beq $t2, $t5, c_number_error
	multu $t4, $t5
	mflo $t4
	addu $t4, $t4, $t2
	j c_number_loop
c_number_nine:
	subu $t2, $t2, 48
	bgt $t2, $t5, c_number_error
	beq $t2, $t5, c_number_error
	multu $t4, $t5
	mflo $t4
	addu $t4, $t4, $t2
	j c_number_loop
c_number_return:
	mult $t4, $t3
	mflo $t4
	push_fp $t4
	pop $t0
	next
c_number_end:
	pop $t0
	jal print_ok
	pop $ra
	j c_quit
c_number_error:
	pop $a0
	jal print_unknow
	pop $ra
	j c_quit
	
		
# CONVERT (n1 addr1 -- n2 addr2) converts an string into a digit, n2 is the digit, addr2 is an invalid caracter
c_convert:
	push $ra
	pop_fp $t0 #init value
	pop_fp $t1 # addr
	addiu $t1, $t1, 2
	la $t3, var_base
	lw $t3, ($t3)
	li $t5, 0
c_convert_loop:
	lbu $t2, ($t1)
	push_fp $t2 #char
	push_fp $t3 #base
	jal c_digit
	pop_fp $t4
	beqz $t4, c_convert_error
	pop_fp $t4
	mult $t5, $t3
	mflo $t5
	add $t5, $t5, $t4
	addiu $t1, $t1, 1
	j c_convert_loop
c_convert_error:
	push_fp $t5
	push_fp $t1
	next
	
# : DIGIT ( char , base -- n2, TF) or ( char , base -- FF)
c_digit:# DIGIT
	pop_fp $t0 #Base
	pop_fp $t1 #Char
	blt $t1, 48, c_digit_error
	blt $t1, 58, c_digit_direct # 0-9
	blt $t0, 10, c_digit_error
	blt $t1, 65, c_digit_error
	blt $t1, 91, c_digit_letter # a-z
	j c_digit_error
c_digit_direct:
	sub $t1, $t1, 48
	bgt $t1, $t0, c_digit_error
	beq $t1, $t0, c_digit_error
	li $t0, 1
	push_fp $t1
	push_fp $t0
	return
c_digit_letter:
	sub $t1, $t1, 55
	bgt $t1, $t0, c_digit_error
	beq $t1, $t0, c_digit_error
	li $t0, 1
	push_fp $t1
	push_fp $t0
	return
c_digit_error:
	push_fp $zero
	return
		
c_words:
	push $ra
	la $t0, d_forth
c_words_next_word:
	push_fp $t0
	push $t0
	push $t1
	jal print_string
	jal print_space
	pop $t1
	pop $t0
	lbu $t1, 0($t0)
	andi $t1, 31
	addu $t0, $t0, 1
	addu $t0, $t0, $t1
	addu $t0,$t0, 3
	andi $t0,$t0, 0xfffffffc
	lw $t0, 0($t0)
	bnez $t0, c_words_next_word
	next
	
# Ejecuta una lista de direcciones a subrutinas hasta encontrar c_exit ( -- )
#run a sequence of addr to sub rutines
do_inst:
	push $ra
do_inst_loop:
	la $t0, var_pointer
	lw $t1, ($t0)
	addiu $t1, $t1, 4
	push $t1
	lw $t2 ($t1)
	sw $t2,($t0)
	lw $t2 ($t2)
	push_fp $t2
	jal c_execute
	pop $t1
	sw $t1, var_pointer
	j do_inst_loop
	
# called when a variable is typed, returns the var addrs ( -- addr)
read_var:
	lw $t1, var_pointer
	addiu $t1, $t1, 4
	push_fp $t1
	return
	
# called when a constant variable is typed, returns the var value ( -- n)
read_const:
	lw $t1, var_pointer
	addiu $t1, $t1, 4
	lw $t0, ($t1)
	push_fp $t0
	return
	
# called when a compiled word is typed, iterates a list of address to subrutines executing them
c_run_program:
	push $ra
	
	next
	
# is placed on the end of a list of subroutines to execute by c_run_program
c_run_end:
	push $ra
	
	next
	
c_cor_compile:#TODO

#?Stack
c_q_stack:
	move $t0, $fp
	la $t1, fp_stack
	sub $t1, $t1, $t0
	sgt $t0, $t1, 256
	beqz $t0, c_q_stack_exit
	j stack_overflow
c_q_stack_exit:
	return
# LIT
c_lit:
	la $t2, var_pointer
	lw $t1, ($t2)
	lw $t0, 4($t1)
	push_fp $t0
	pop $t2
	addiu $t2, $t2, 4
	push $t2
	return
	
# BEGIN
c_begin:
	la $t1, var_pointer
	lw $t0, ($t1)
	push_fp $t0
	return

# AGAIN
c_again:
	la $t1,var_pointer
	pop_fp $t0
	sw $t0, ($t1)
	return
	
#BRANCH (flag --) branch to addr when flag is false, use: branch addr
c_branch:
	stack_check 4
	pop_fp $t0
	beqz $t0, c_branch_false
	la $t1, var_pointer
	lw $t0, ($t1)
	addiu $t0, $t0, 4
	sw $t0, ($t1)
	return
c_branch_false:
	la $t1, var_pointer
	lw $t0, ($t1)
	lw $t2, 4($t0)
	sw $t2, ($t1)
	return
	
# IF	
c_if:
	la $t0, var_compilee
	lw $t1, ($t0)	# t1 addr where the word shoud be compiled
	la $t2, p_branch
	sw $t2, ($t1)	# t2 stored the addr of BRANCH
	li $t2, 0
	sw $t2, 4($t1) # stores 0 in the jump space
	addiu $t3, $t1, 4
	la $t2, var_if
	sw $t3, ($t2)# stores in var_if the addr of the jump addr
	addiu $t1, $t1, 8
	sw $1, ($t0)
	return

#ELSE
c_else:
	la $t1, var_if
	lw $t0, ($t1)
	la $t2, var_compilee
	lw $t2, ($t2)
	la $t3, p_int_else
	sw $t3, ($t2)
	addiu $t3, $t2, 4
	sw $t3, ($t1)
	addiu $t2, $t2, 8
	sw $t2, ($t0)
	return

# THEN
c_then:
	la $t1, var_if
	lw $t0, ($t1)
	la $t2, var_compilee
	lw $t2, ($t2)
	sw $t2, ($t0)
	return
	
#ELSE Jump
c_int_else:
	la $t0, var_pointer
	lw $t1, ($t0)
	addiu $t1, $t1, 4
	lw $t2, ($t1)
	sw $t2, ($t0)
	return
	
# , COMMA (n -- ) escribe n en HERE
c_comma:
	push $ra
	stack_check 4
	jal c_alignw
	lw $t1, var_here
	pop_fp $t0
	sw $t0, ($t1)
	li $t0, 4
	push_fp $t0
	jal c_allot
	next
	
#Nucleus layer

# ."
c_dot_quote:
	push $ra
	li $t0, 34
	push_fp $t0
	jal c_word
	jal print_string
	jal print_space
	next

# "
c_quote:
	return
# HEX
c_hex:
	li $t0, 16
	sw $t0, var_base
	return
# DEC
c_dec:
	li $t0, 10
	sw $t0, var_base
	return

# FREE
c_free:
	sw $t0, var_here
	li $t2, 0
c_free_loop:
	lb $t1, ($t0)
	bnez $t1, c_free_exit
	addiu $t0, $t0, 1
	addi $t2, $t2, 1
	j c_free_loop	
c_free_exit:
	push_fp $t2
	return

# PAGE clear the screen ( -- )
c_page:
	push $ra
	jal clearScreen
	next

# CR makes a line jump ( -- )
c_cr:
	push $ra
	jal jumpLine
	next

# [ leaves compilation mode ( -- )
c_bracket_1:
	sw $zero, var_state
	return
	
# ] enters compilation mode ( -- )
c_bracket_2:
	li $t0, 1
	sw $t0, var_state
	return
	
# ! escribe un valor (n addr --)
c_set:
	stack_check 8
	pop_fp $t0	# Addr
	pop_fp $t1	# Num
	sw $t1, ($t0)
	return
	
# * multiplica dos valores del stack (n1 n2 -- n3) n3 = n1*n2
c_star:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	mult $t1, $t0
	mflo $t2
	push_fp $t2
	return
	
# */ (n2 n1 n0 -- n3)
# multiplica dos valores del stack n2 y n1 y divide el resultado por n0
c_star_slash:
	stack_check 12
	pop_fp $t0
	pop_fp $t1
	pop_fp $t2
	mult $t2, $t1
	mflo $t3
	div $t3, $t0
	mflo $t2
	push_fp $t2
	return
	
# */MOD multiplica dos valores del stack n1 y n2 y divide el resultado por n3, n5 es el modulo (n1 n2 n3 -- n4 n5) == n1 n2 * n3 /
c_star_slash_mod:
	stack_check 16
	pop_fp $t0
	pop_fp $t1
	pop_fp $t2
	mult $t1, $t0
	mflo $t3
	div $t3, $t2
	mflo $t2
	mfhi $t3
	push_fp $t2
	push_fp $t3
	return
	
# + suma los ultimos valores del stack (n1 n2 -- n3)
c_plus:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	add $t2, $t1, $t0
	push_fp $t2
	return
	
# +! añade n al valor en la pos addr (n addr --)
c_plus_set: 
	stack_check 8
	pop_fp $t0 # addr
	pop_fp $t1 # num
	lw $a0, ($t0)
	add $a0, $a0, $t1
	sw $a0, ($t0)
	return
	
# - (n1 n2 -- n3)
#resta dos numeros en el stack n3 = n1 - n2
c_minus:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	sub $t0, $t1, $t0
	push_fp $t0
	return
	
# / divide dos valores del stack (n1 n2 -- n3) n3 = n1/n2
c_slash:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	div $t1, $t0
	mflo $t2
	push_fp $t2
	return
	
# /MOD divide n1 by n2 and leave the remainder(n1 n2 -- n3 n4)
c_slash_mod:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	div $t1, $t0
	mflo $t1
	mfhi $t2
	push_fp $t2
	push_fp $t1
	return

# 0< devuelve 1 si n1 es menor que cero, sino 0 (n1 -- n2)
c_zero_less:
	stack_check 4
	pop_fp $t0
	slt $t1, $t0, $zero
	push_fp $t1
	return
	
# 0= devuelve 1 si n1 es igual cero, sino 0(n1 -- n2)
c_zero_equal:
	stack_check 4
	pop_fp $t0
	seq $t1, $t0, $zero
	push_fp $t1
	return

# 0> devuelve 1 si n1 es mayor que cero, sino 0 (n1 -- n2)
c_zero_more:
	stack_check 4
	pop_fp $t0
	sgt $t1, $t0, $zero
	push_fp $t1
	return

# 1+ añade 1 (n1 -- n2) == (1 + )
c_one_plus:
	stack_check 4
	pop_fp $t0
	addi $t0, $t0, 1
	push_fp $t0
	return
	
# 1- resta 1 (n1 -- n2) == (1 - )
c_one_minus:
	stack_check 4
	pop_fp $t0
	sub $t0, $t0, 1
	push_fp $t0
	return
	
# 2+ suma 2 a n1 (n1 -- n2)
c_two_plus:
	stack_check 4
	pop_fp $t0
	addi $t0, $t0, 2
	push_fp $t0
	return

# 2- resta 2 a n1 (n1 -- n2)
c_two_minus:
	stack_check 4
	pop_fp $t0
	sub $t0, $t0, 2
	push_fp $t0
	return
	
# 2/ divide por 2 a n1 (n1 -- n2)
c_two_slash:
	stack_check 4
	pop_fp $t0
	sra $t0, $t0, 1
	push_fp $t0
	return
	
# < devuelve 1 si n1 es menor que n2 (n1 n2 -- n3)
c_less:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	slt $t0, $t1, $t0
	push_fp $t0
	return
	
# = devuelve 1 si n1 y n2 son iguales (n1 n2 -- n3)
c_equal:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	seq $t0, $t1, $t0
	push_fp $t0
	return

# > devuelve 1 si n1 es mayor que n2 (n1 n2 -- n3)
c_more:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	sgt $t0, $t1, $t0
	push_fp $t0
	return
	
# >R pasa el valor de la cima del stack fp al stack sp (n --)
c_more_r:
	stack_check 4
	pop_fp $t0
	push $t0
	return

# ?DUP llama a dup si no hay un 0 en el stack (n -- n n) o ( n --)
c_q_dup:
	stack_check 4
	pop_fp $t0
	beqz $t0, c_q_dup_1
	push_fp $t0
	push_fp $t0
c_q_dup_1:
	push_fp $t0
	return

# @ lee un dato de memoria (add -- num)
c_at:
	stack_check 4
	pop_fp $t0
	lw $t1, ($t0)
	push_fp $t1
	return
	
# ABS devuelve el valor absoluto de n1 (n1 -- n2)
c_abs:
	stack_check 4
	pop_fp $t0
	abs $t1, $t0
	push_fp $t1
	return
	
# AND devuelve el and de n1 y n2 (n1 n2 -- n3)
c_and:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	and $t0, $t1, $t0
	push_fp $t0
	return

# C! almacena los 8 MSB del n1 en addr (n1 addr --)
c_c_set:
	stack_check 8
	pop_fp $t0 # Adrr
	pop_fp $t1 # num
	sb $t1, ($t0)
	return

# C@ devuelve los 8 MSB en addr (addr -- n1)
c_c_at:
	stack_check 4
	pop_fp $t0
	lb $t0, 0($t0)
	push_fp $t0
	return

# CMOVE copy n bytes from a1 to a2 from 0 to n-1(a1 a2 n --)
c_cmove:
	stack_check 12
	pop_fp $t0 # u
	pop_fp $t1 # a2
	pop_fp $t2 # a1
c_cmove_2:
	beqz $t0, c_cmove_1
	lbu $a0, ($t2)
	sb $a0, ($t1)
	addi $t1, $t1, 1
	addi $t2, $t2, 1
	subu $t0, $t0, 1
	j c_cmove_2
c_cmove_1:
	return
	
# CMOVE> copy n bytes from a1 to a2 from n-1 to 0(a1 a2 n --)
c_cmove_more:
	stack_check 12
	pop_fp $t0 # u
	pop_fp $t1 # a2
	pop_fp $t2 # a1
	addu $t1, $t1, $t0
	addu $t2, $t2, $t0
	subu $t1, $t1, 1
	subu $t2, $t2, 1
c_cmove_more_2:
	beqz $t0, c_cmove_more_1
	lbu $a0, ($t2)
	sb $a0, ($t1)
	subu $t1, $t1, 1
	subu $t2, $t2, 1
	subu $t0, $t0, 1
	j c_cmove_2
c_cmove_more_1:
	return

# COUNT (addr1 -- addr2 n1) #TODO
# dada una entrada de dict, devuelve el addr del nombre y el numero de caracters, devuelve addr1+1 
c_count:
	stack_check 4
	pop_fp $t0 #Addr
	lb $t2, ($t0)
	addiu $t1, $t0, 1
	push_fp $t1 #addr2
	push_fp $t2 # n
	return
	
# D+ double plus, suma dos doubles,(n1 n2 -- n3)siendo n1 n2 n3 doubles (64 bits)
c_d_plus:
	stack_check 16
	pop_fp $t0 # n1
	pop_fp $t1 # n2
	pop_fp $t2 # a1
	pop_fp $t3 # a2
	add $t1, $t1, $t0
	add $t0, $t3, $t2
	push_fp $t0
	push_fp $t1
	return
# D< double less, igual que < pero con dobles, s,(n1 n2 -- n3)
c_d_less:
	stack_check 16
	pop_fp $t0 # n1
	pop_fp $t1 # n1
	
	pop_fp $t2 # n2
	pop_fp $t3 # n2
	
	beq $t0, $t2, c_d_less_check
	blt $t0, $t2, c_d_less_less
c_d_less_check:
	bltu $t1, $t3, c_d_less_less # if the two numbers are negative the result is incorrect
	li $t0, 0
	push_fp $t0
c_d_less_less:
	li $t0, 1
	push_fp $t0
	return
	
# DEPTH numero e elementos en el stack antes de poner n1( -- n1)
c_depth:
	move $t0, $fp
	la $t1, fp_stack
	sub $t1, $t1, $t0
	srl $t1, $t1, 2 
	push_fp $t1
	return
	
# DNEGATE devuelve el complemento a2 del double(long) n1 (n1 -- n2)
c_dnegate:
	stack_check 4
	pop_fp $t0
	neg $t1, $t0
	push_fp $t1
	return

# DROP elimina el ultimo numero del stack (n1 --)
c_drop:
	stack_check 4
	pop_fp $zero
	return
	
# DUP duplica el top del stack (n1 -- n1 n1)
c_dup:
	stack_check 4
	pop_fp $t0
	push_fp $t0
	push_fp $t0
	return
	
# EXECUTE ejecuta una palabra en la dir addr(addr --)
c_execute:
	stack_check 4
	pop_fp $t0
	sw $t0, var_pointer
	lw $t1, ($t0)
	jr $t1
	
# EXIT al finalizar una definicion( -- )
c_exit:
	next
	
# FILL llena n bytes de memoria con b en la dir addr(addr n b --)
c_fill:
	stack_check 12
	pop_fp $t0 # byte
	pop_fp $t1 # num
	pop_fp $t2 # addr
c_fill_loop:
	beqz $t1, c_fill_exit
	sb $t0, ($t2)
	addiu $t2, $t2, 1
	sub $t0, $t0, 1
	j c_fill_loop
c_fill_exit:
	return
	
# I devuelve el index del loop ( -- n)
c_i:
	j c_r_at # R@ lee el stack sp

# J devuelve el index del loop exterior ( -- n) DO ... DO ... J ... LOOP ... +LOOP
c_j:
	return #TODO
	
# MAX (n1 n2 -- n3) n3 es el mayor de n1 y n2
c_max:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	bgt $t0, $t1, c_max_1
	push_fp $t1
	return
c_max_1:
	push_fp $t0
	return
	
# MIN (n1 n2 -- n3) n3 es el menor de n1 y n2
c_min:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	blt $t0, $t1, c_min_1
	push_fp $t1
	return
c_min_1:
	push_fp $t0
	return
	
# MOD (n1 n2 -- n3) n3 = n1 % n2
c_mod:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	div $t1, $t0
	mfhi $t0
	push_fp $t0
	return
	
# NEGATE devuelve el complemento a2 del numero n1 (n1 -- n2)
c_negate:
	stack_check 4
	pop_fp $t0
	neg $t1, $t0
	push_fp $t1
	return

# NOT (n1 -- n2) n2 es el complemento a1 de n1
c_not:
	stack_check 4
	pop_fp $t0
	not $t0, $t0
	push_fp $t0
	return

# OR (n1 n2 -- n3)n3 = n1 | n2
c_or:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	or $t0, $t0, $t1
	push_fp $t0
	return
	
# OVER (n1 n2 -- n1 n2 n1) copia el elemento segundo en el stack y lo pone de primero
c_over:
	stack_check 8
	pop_fp $t1
	pop_fp $t0
	push_fp $t0
	push_fp $t1
	push_fp $t0
	return

# PICK (n1 -- n2) n2 es la copia del n-esimo elemento del stack
c_pick:
	stack_check 4
	push $ra
	pop_fp $t0
	bltz $t0, c_pick_error
	push $t0
	jal c_depth
	pop_fp $t1
	pop $t0
	bgt $t0, $t1, c_pick_error
	li $t1, 4
	mult $t0, $t1
	mflo $t0
	addu $t0, $fp, $t0
	lw $t1, ($t0)
	push_fp $t1
	next
c_pick_error:
	push_fp $zero
	next

# R> pasa el valor de la cima del stack sp al stack fp ( -- n1)
c_r_more:
	pop $t0
	push_fp $t0
	return
	
# R@ or SP@ lee de el stack sp al fp ( -- n)
c_r_at:
	lw $t0, ($sp)
	push_fp $t0
	return
	
# ROLL (n --) introduce en la cima del stack el elemento n-esimo, extrayendolo de su posicion
c_roll:#REDO
	stack_check 4
	pop_fp $t0
	bltz $t0, c_roll_error
	li $t1, 4
	mult $t0, $t1
	mflo $t0
	add $t2, $t0, $fp
	lw $t1, ($t2)
c_roll_loop:
	beqz $t0, c_roll_exit
	add $t3, $t0, $fp
	lw $t2, 4($t3)
	sw $t2, ($t3)
	sub $t0, $t0, 4
	j c_roll_loop
c_roll_exit:
	pop_fp $t0
	push_fp $t1
	return
c_roll_error:
	return

# ROT (n1 n2 n3 -- n2 n3 n1)
c_rot: 
	pop_fp $t3
	pop_fp $t2
	pop_fp $t1
	push_fp $t2
	push_fp $t3
	push_fp $t1
	return
	
# SWAP (n1 n2 -- n2 n1)
c_swap:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	push_fp $t0
	push_fp $t1
	return
	
# U< menor unsigned ( n1 n2 -- n3)
c_u_less:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	sltu $t0, $t1, $t0
	push_fp $t0
	return
	
# UM* multiplicacion sin signo (n1 n2 -- n3)
c_um_star: 
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	multu $t0, $t1	
	mflo $t1
	push_fp $t1
	return
	
# UM/MOD (n1 n2 -- n3 n4)n3 = n1 % n2, n4 = n1 / n2
c_um_slash_mod:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	divu $t1, $t0
	mflo $t2
	mfhi $t1
	push_fp $t1
	push_fp $t2
	return
	
# XOR (n1 n2 -- n3)
c_xor:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	xor $t0, $t0, $t1
	push_fp $t0
	return
	
# <> devuelve 1 si n1 es distinto de n2 (n1 n2 -- n3)
c_not_equal:
	stack_check 8
	pop_fp $t0
	pop_fp $t1
	sne $t0, $t1, $t0
	push_fp $t0
	return
	
#Interpreter layer

# imprime un entero (n --)
print_int:
	stack_check 4
	push $ra
	push $s0
	jal c_less_hash # <#
	pop_fp $s0
	push_fp $s0
	jal c_hash_s # #S
	bltz $s0, print_int_negative
	j print_int_no_negative
print_int_negative:
	pop_fp $zero
	li $t0, 45
	push_fp $t0
print_int_no_negative:
	jal c_hash_more# #>
	jal c_type
	pop $s0
	next
	
# <# ( -- ) limpia el output buffer
c_less_hash:
	sb $zero, output_buf
	return

# # (n1 -- n2) halla n1 % base, lo guarda en el strign de salida y devuelve el cociente de la division
c_hash:
	stack_check 4
	pop_fp $t0
	lw $t1, var_base
	div $t0, $t1
	mflo $t1 # div return
	mfhi $a0 # mod
	addi $a0,$a0,48
	li $t2, 58
	blt $a0, $t2, c_hash_no_letters
	addi $a0,$a0,7
c_hash_no_letters:
	la $t0, output_buf
	lb $t2, ($t0) # chars
	li $t3, 126   # tam
	sub $t3, $t3, $t2 # n pos
	addu $t3, $t3, $t0 # addr pos
	sb $a0, ($t3)	#store char
	addi $t2, $t2, 1
	sb $t2, ($t0)
	push_fp $t1
	return

# #> (n1 -- addr n2) recive el caracter final del string(el del principio) y devuelve la addr del string de salida
c_hash_more:
	stack_check 4
	pop_fp $t1
	la $t0, output_buf
	lb $t2, ($t0) # num of chars
	li $t3, 126   # tam
	sub $t3, $t3, $t2
	addu $t3, $t3, $t0
	addi $t3, $t3, 1
	beqz $t1, c_hash_more_return
	sb $t1, ($t3)
	addi $t2, $t2, 1
	subi $t3, $t3, 1
c_hash_more_return:
	push_fp $t3
	push_fp $t2
	return
	
# #S (n -- 0) 
#crea un string en output_buffer a partir de n no incluye el signo
c_hash_s:
	stack_check 4
	push $ra
	pop_fp $t0
	beqz $t0, c_hash_s_cero
c_hash_s_loop:
	beqz $t0, c_hash_s_exit
	push_fp $t0
	jal c_hash
	pop_fp $t0
	j c_hash_s_loop
c_hash_s_cero:
	la $t0, output_buf
	li $t2, 1
	li $t3, 48 # 0 char
	sb $t3, 126($t0)
	sb $t2, ($t0)
c_hash_s_exit:
	push_fp $zero
	next
	
# ' (-- addr) addr el la dir de compilacion del codigo que viene despues de el, uso: ' <name>
c_tick: #TODO buscar name en el dict y devolver su compilation addres
	return
	
# ( coments used in the form( soy un comentario) ( -- )
c_paren: 
	push $ra
	li $t0, 41
	push_fp $t0
	jal c_word
	pop_fp $t0
	next
	
# )
c_inv_paren:
	return

# -TRAILING (addr1 n1 -- addr1 n2) elimina los spacios al inicio del string, addr1 string, n1 tamaño,n2 nuevo tamaño
c_minus_trailing:
	stack_check 8
	pop_fp $t0 # tam	#$t0 tam incial, $t1 addr, $t4 addr, $t2 char to compare, $t3 char readed, $t5 init tam
	pop_fp $t4 # addr
	move $t1, $t4 # t1  addr
	move $t5, $t0# t5 initial tam
	beqz $t0,c_minus_trailing_exit # si el tamaño es cero return
	li $t2, 32 # se carga el caracter spacio
c_minus_trailing_loop: # se cuentan los espacios
	lb $t3, ($t1)	
	bne $t3,$t2,c_minus_trailing_loop_exit 
	addiu $t1,$t1,1 # avanza de char
	sub $t0, $t0, 1 # tamaño--
	j c_minus_trailing_loop # t1 addr + num of spaces, t0 new tam
c_minus_trailing_loop_exit:# se mueve el string
	sub $t5, $t5, $t0 # $t5 loop index, tam incial - fin
	move $t2, $t4 # t2 addr moved in the loop
c_minus_trailing_loop_2:#moving string
	beqz $t5, c_minus_trailing_exit
	lb $t3, ($t1)
	sb $t3, ($t2)
	addiu $t1,$t1,1
	addiu $t2,$t2,1
	sub $t5,$t5,1
	j c_minus_trailing_loop_2
c_minus_trailing_exit:
	pop_fp $t4 # addr
	pop_fp $t0 # tam
	return

# . (n -- )hace pop del tos y lo imprime en pantalla
c_dot:
	push $ra
	jal print_int
	jal print_space
	next

# .S (--)
# imprime los valores del stack
c_dot_s:
	push $ra
	push $s0
	push $s1
	push $s2
	jal c_depth  # obtiene el tamaño del stack
	pop_fp $s0
	beqz $s0, c_dot_s_error
	move $s1, $s0
c_dot_s_loop:
	beqz  $s0, c_dot_s_loop_1
	pop_fp $s2
	push $s2
	sub $s0, $s0, 1	
	j c_dot_s_loop
c_dot_s_loop_1:
	beqz  $s1, c_dot_s_exit
	pop $s0
	push_fp $s0
	push_fp $s0
	jal print_int
	jal print_space
	sub $s1, $s1, 1	
	j c_dot_s_loop_1
c_dot_s_exit:
	pop $s2
	pop $s1
	pop $s0
	next
c_dot_s_error:
	la $a0, stack_empty_msg
	push_fp $a0
	jal print_string
	jal print_space
	pop $s2
	pop $s1
	pop $s0
	next

# .(  imprime un string en pantalla uso: .( hola k ase)   (--)
c_dot_paren:
	return

# >BODY (addr1 -- addr2) a partid de la addr de el codigo obtiene la de la variable 
c_more_body:
	stack_check 4
	pop_fp $t0
	addi $t0, $t0, 8
	push_fp $t0
	return
	
# SPACE (--)
c_space:
	push $ra
	jal print_space
	next
	
# 0! escribe 0 en addr (addr --)
c_zero_set:
	stack_check 4
	pop_fp $t0
	sw $zero, ($t0)
	return
	
# ENCLOSE (addr c -- addr n1 n2 n3) text escaner, addr string, c elimitador, n1(hasta c) n2(hasta el n-esimo c) n3(haste el segundo c despues de los consecutivos de n2) offsets
c_enclose:
	stack_check 4
	pop_fp $t0 # Char
	pop_fp $t1 # Addr
	
	push_fp $t1
	push_fp $t0
	
	li $s0, 0 # n1
	li $s1, 0 # n2
	li $s2, 0 # n3
	li $t7, 10 # eof new line char
c_enclose_2:
	lbu $a0, ($t1) #addr -> a0
	beq $a0, $t7, c_enclose_eof # if salto de linea, exit
	beq $a0, $t0, c_enclose_1 # if char delimitador
	addu $t1, $t1, 1
	addu $s0, $s0, 1
	addu $s1, $s1, 1
	addu $s2, $s2, 1
	j c_enclose_2
c_enclose_1:
	addu $t1, $t1, 1
	addu $s1, $s1, 1
	addu $s2, $s2, 1
	lbu $a0, ($t1)
	beq $a0, $t7, c_enclose_eof
	beq $a0, $t0, c_enclose_1 # while char == delimitador
c_enclose_3:
	addu $t1, $t1, 1
	addu $s2, $s2, 1
	lbu $a0, ($t1)
	beq $a0, $t7, c_enclose_eof
	beq $a0, $t0, c_enclose_3
c_enclose_eof:
	push_fp $s0
	push_fp $s1
	push_fp $s2
	return
	
# ALIGN same as .align con here (n --)
c_align:
	stack_check 4
	pop_fp $t0
	subu $t0,$t0, 1

	lw $a0, var_here
	addu $a0, $a0, $t0
	not $t0, $t0
	and $a0, $a0, $t0
	sw $a0, var_here
	return
	
# ALIGNW == (4 ALIGN) (--)
c_alignw:
	push $ra
	li $a0, 4
	push_fp $a0
	jal c_align
	next
	
# ALIGNH == (2 ALIGN) (--)
c_alignh:
	push $ra
	li $a0, 2
	push_fp $a0
	jal c_align
	next
	
# ALLOT (n -- addr)
c_allot:
	stack_check 4
	pop_fp $t0 # n bytes
	lw $a0, var_here
	addu $a0, $a0, $t0
	sw $a0, var_here
	return
	
# DP! (n --)pone n en HERE
c_dp_set:
	stack_check 4
	pop_fp $t0
	sw $t0, var_here
	return

# 2DUP (n1 n2 -- n1 n2 n1 n2 )	
c_two_dup:
	pop_fp $t1
	pop_fp $t0
	push_fp $t0
	push_fp $t1 
	push_fp $t0
	push_fp $t1 
	return
	
c_int_do:# (DO)
	stack_check 8
	pop_fp $t0 # index
	pop_fp $t1 # limit
	push $t0 
	push $t1
return
	
# ALPHA (n1 -- n2) n2 is the char asocited with n1
c_alpha:
	stack_check 4
	pop_fp $t0
	andi $t0, $t0, 0xFF
	li $t1, 10
	blt $t0, $t1, c_alpha_nine
	addi $t0, $t0, 55
	j c_alpha_end
c_alpha_nine:
	addi $t0, $t0, 48
c_alpha_end:
	push_fp $t0
	return
	
c_int_loop:# (LOOP)
	lw $t0, 0($sp)		# index
	lw $t1, 4($sp)		# limit
	addi $t0,$t0, 1
	bge $t0, $t1, c_int_loop_break
	sw $t0, 0($sp)
	lw $s0, 0($ra)
	jr $s0
c_int_loop_break:
	addu	$sp,$sp, 8
	return
	
c_rdrop:# RDROP
	pop $zero
	return

# : LITERAL   ( n1 -- ) compile un numero del stack
c_literal:
	lw $t1, var_state
	beqz $t1, c_literal_end
	pop_fp $t0
	lw $t2, var_here
	la $t3, p_lit
	sw $t3, ($t2)
	addiu $t2, $t2, 4
	sw $t0, ($t2)
	addiu $t2, $t2, 4
	sw $t2, var_here
c_literal_end:
	return

# QUIT (--)
c_quit:
	la $sp, sp_stack
	sw $zero, var_state
	j c_forth
	
print_unknow: # print unknow token
	push $a0
	la $a0, unknow_msg
	push_fp $a0
	jal print_string
	pop $a0
	push_fp $a0
	jal print_string
	j c_quit
	
print_ok:# print ok
	la $a0, ok_msg
	push_fp $a0
	jal print_string
	j c_quit
	
stack_empty:# print stack empty
	la $a0, stack_empty_msg
	push_fp $a0
	jal print_string
	j c_quit
	
stack_overflow:# print stack overflow
	la $a0, stack_overflow_msg
	push_fp $a0
	jal print_string
	j c_quit
	
c_block:# BLOCK
	return

c_compile:
	return
	
	
c_scr:# VARIABLE SCR var usada por LIST
	la $t0, var_scr
	push_fp $t0
	return

	
# prints a counted string (addr --)
print_string:
	push $ra
	pop_fp $t0
	lbu $t1, ($t0)
	addiu $t0, $t0, 1
	andi $t1, $t1, 127
	push_fp $t0 #addr
	push_fp $t1 #num
	jal c_type
	next
	
print_space:
	push $ra
	li $a0, 32
	jal print_char
	next

# TYPE (addr num --)
# write an amount (num) of characteres from addr into the terminal (addr num --)
c_type:
	stack_check 8
	push $ra
	pop_fp $t1 # Num
	pop_fp $t0 # Addr
c_type_loop:
	beqz $t1, c_type_exit
	sub $t1, $t1, 1
	lbu $a0, ($t0)
	addiu $t0, $t0, 1
	push $t1
	push $t0
	jal print_char
	pop $t0
	pop $t1
	j c_type_loop
c_type_exit:
	next

print_tib:	
	push $ra	
	la $t0, var_tib
	li $t1, 80
	push $t1
	push $t0
print_tib_loop:
	pop $t0
	pop $t1
	beqz $t1, print_tib_end
	lbu $t2, ($t0)
	subu $t1, $t1, 1
	addiu $t0, $t0, 1
	push $t1
	push $t0
	push_fp $t2
	jal print_int
	li $a0, 95
	jal print_char
	j print_tib_loop
print_tib_end:
	next

	j c_quit
	
.include "mips_driver.asm"
