
.macro save_ra
	addi $sp, $sp, -4
	sw $ra, ($sp)
.end_macro
.macro load_ra
	lw $ra, ($sp)
	addi $sp, $sp, 4
.end_macro

.macro save_s0
	addi $sp, $sp, -4
	sw $s0, ($sp)
.end_macro
.macro load_s0
	lw $s0, ($sp)
	addi $sp, $sp, 4
.end_macro
.macro return
	jr $ra
.end_macro 
