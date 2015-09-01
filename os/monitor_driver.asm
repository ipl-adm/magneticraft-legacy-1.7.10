
#this script requires macros.asm
.text

isKeyPressed:# return 1 if a key was pressed
	la $t0, 0xFF010000
	lw $t1, ($t0)
	andi $v0, $t1, 1
	return
	
resetPressedKeyFlag:# restet the keyboard to listen again for a key press
	la $t0, 0xFF010000
	li $t1, 0
	sw $t1, ($t0)
	return
	
getKeyPresed:# return the las pressed key
	la $t0, 0xFF010004
	lw $v0, ($t0)
	return
	
getCursorPosition:# return the position of the cursor  
	la $t0, 0xFF010008
	lw $v0, ($t0)
	return
	
setCursorPosition:# args: a0 new position for the cursor
	la $t0, 0xFF010008
	sw $a0, ($t0)
	return
	
setChar:# args: a0 char, a1 position in [0, 4000)
	la $t0, 0xFF010014
	add $t0, $a1, $t0
	sb $a0, ($t0)
	return
	
getChar:# args: a0 position in [0, 4000)
	la $t0, 0xFF010014
	add $t0, $a0, $t0
	lb $v0, ($t0)
	return

clearScreen:
	push $ra
	li $s0, 4000
	li $a0, 32
	li $a1, 0
loop1:	
	jal setChar
	addi $s0, $s0, -1
	addi $a1, $a1, 1
	bne $s0, $zero, loop1 
	li $a0, 0
	jal setCursorPosition
	pop $ra
	return

putChar:#args: a0 character
	la $t0, 0xFF010008
	la $t1, 0xFF010014
	lw $t2, ($t0)
	add $t1, $t1, $t2
	sb $a0, ($t1)
	addi $t2, $t2, 1
	sw $t2, ($t0)
	return
	
removeChar:
	push $ra
	jal getCursorPosition
	li $a0, 32
	addi $a1 $v0, -1
	jal setChar
	addi $a0, $a1, 0
	jal setCursorPosition
	pop $ra
	return

getLine:
	push $ra
	jal getCursorPosition
	li $t1, 80
	div $v0, $t1
	mfhi $t0
	sub $a0, $v0, $t0
	la $t0, 0xFF010014
	addu $v0, $a0, $t0
	pop $ra
	return

jumpLine:
	push $ra
	jal getCursorPosition
	li $t1, 80
	div $v0, $t1
	mfhi $t0
	sub $a0, $v0, $t0
	addi $a0, $a0, 80
	jal setCursorPosition
	pop $ra
	return
	
lecture_loop:
	jal isKeyPressed
	beqz $v0, lecture_loop
	jal resetPressedKeyFlag
	jal getKeyPresed
	beq $v0, 13, lecture_end
	beq $v0, 8, lecture_supr
	bgt $v0, 126, lecture_loop
	beq $s3, $s1, lecture_end
	bgt $s3, $s1, lecture_end
	sb $v0, ($s2)
	addiu $s2, $s2, 1
	addiu $s3, $s3, 1
	addiu $s4, $s4, 1
	move $a0, $v0
	jal putChar
	j lecture_loop
lecture_supr:
	beqz $s4, lecture_loop
	subu $s2, $s2, 1
	subu $s4, $s4, 1
	jal removeChar
	j lecture_loop
lecture_end:
	sb $zero, ($s2)
	jal jumpLine
	j get_raw_input_exit
	
#(num addr -- addr)
get_raw_input:
	push $ra
	push $s0
	push $s1
	push $s2
	push $s3
	push $s4
	li $s4, 0
	pop_fp $s0 # addr
	pop_fp $s1 # max
	push_fp $s0
	move $s2, $s0
	li $s3, 0
	j lecture_loop
get_raw_input_exit:
	pop $s4
	pop $s3
	pop $s2
	pop $s1
	pop $s0
	next
	
print_char:
	push $ra
	beq $a0, 13, print_char_jump
	beq $a0, 10, print_char_jump
	jal putChar
	next
print_char_jump:
	jal jumpLine
	next
