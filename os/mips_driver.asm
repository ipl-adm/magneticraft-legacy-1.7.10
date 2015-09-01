
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
	return

putChar:#args: a0 character
	li $v0, 11
	syscall
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
	li $a0, 10
	li $v0, 11
	syscall
	return
	
#(num addr -- addr)
get_raw_input:
	li $v0, 8
	pop_fp $a0
	pop_fp $a1
	syscall
	push_fp $a0
	return
	
print_char:
	push $ra
	li $t0, 13
	beq $a0, $t0, print_char_jump
	li $t0, 10
	beq $a0, $t0, print_char_jump
	jal putChar
	next
print_char_jump:
	jal jumpLine
	next
