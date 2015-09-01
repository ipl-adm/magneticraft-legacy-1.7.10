
.text
printString:#args: a0 string pointer
	push $ra
	push $s0
	addi $s0, $a0, 0
loopStr:
	lb $a0, ($s0)
	beq $zero, $a0, exitLoop
	addi $s0, $s0, 1
	jal putChar
	j loopStr
exitLoop:
	pop $s0
	pop $ra
	return
	
printInt:#args: a0 int to print
	push $ra
	push $s0
	push $s1
	li $t1, 10
	addi $s0, $a0, 0 #save the number
	li $s1, 0
intloop:
	divu  $s0, $t1 # mod 10
	mfhi $t0 # save number/10
	mflo $s0 # push number % 10
	addi $t0 $t0, 48 # to ascii
	push $t0# push to stack
	addi $s1, $s1, 1
	bne $s0, $zero, intloop
while_0:#while $s1 != 0
	lw $a0, ($sp)
	pop $zero
	jal putChar
	addi $s1, $s1, -1
	bne $s1, $zero, while_0 
	pop $s1
	pop $s0
	pop $ra
	return
	

