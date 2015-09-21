	.file	1 "main.c"
	.section .mdebug.abi32
	.previous
	.nan	legacy
	.gnu_attribute 4, 1
	.abicalls
	.option	pic0
	.text
	.align	2
	.globl	forth
	.set	nomips16
	.set	nomicromips
	.ent	forth
	.type	forth, @function
forth:
	.frame	$sp,0,$31		# vars= 0, regs= 0/0, args= 0, gp= 0
	.mask	0x00000000,0
	.fmask	0x00000000,0
	.set	noreorder
	.set	nomacro
	j	$31
	nop

	.set	macro
	.set	reorder
	.end	forth
	.size	forth, .-forth
	.align	2
	.globl	push
	.set	nomips16
	.set	nomicromips
	.ent	push
	.type	push, @function
push:
	.frame	$sp,0,$31		# vars= 0, regs= 0/0, args= 0, gp= 0
	.mask	0x00000000,0
	.fmask	0x00000000,0
	.set	noreorder
	.set	nomacro
	lui	$3,%hi(fp)
	lw	$2,%lo(fp)($3)
	sw	$4,0($2)
	addiu	$2,$2,4
	j	$31
	sw	$2,%lo(fp)($3)

	.set	macro
	.set	reorder
	.end	push
	.size	push, .-push
	.align	2
	.globl	pop
	.set	nomips16
	.set	nomicromips
	.ent	pop
	.type	pop, @function
pop:
	.frame	$sp,0,$31		# vars= 0, regs= 0/0, args= 0, gp= 0
	.mask	0x00000000,0
	.fmask	0x00000000,0
	.set	noreorder
	.set	nomacro
	lui	$4,%hi(fp)
	lw	$3,%lo(fp)($4)
	addiu	$3,$3,-4
	lw	$2,4($3)
	j	$31
	sw	$3,%lo(fp)($4)

	.set	macro
	.set	reorder
	.end	pop
	.size	pop, .-pop
	.align	2
	.globl	abortStacks
	.set	nomips16
	.set	nomicromips
	.ent	abortStacks
	.type	abortStacks, @function
abortStacks:
	.frame	$sp,0,$31		# vars= 0, regs= 0/0, args= 0, gp= 0
	.mask	0x00000000,0
	.fmask	0x00000000,0
	.set	noreorder
	.set	nomacro
	lui	$2,%hi(spStack)
	lui	$3,%hi(sp)
	addiu	$2,$2,%lo(spStack)
	sw	$2,%lo(sp)($3)
	lui	$2,%hi(fpStack)
	lui	$3,%hi(fp)
	addiu	$2,$2,%lo(fpStack)
	j	$31
	sw	$2,%lo(fp)($3)

	.set	macro
	.set	reorder
	.end	abortStacks
	.size	abortStacks, .-abortStacks
	.align	2
	.globl	checkStack
	.set	nomips16
	.set	nomicromips
	.ent	checkStack
	.type	checkStack, @function
checkStack:
	.frame	$sp,0,$31		# vars= 0, regs= 0/0, args= 0, gp= 0
	.mask	0x00000000,0
	.fmask	0x00000000,0
	.set	noreorder
	.set	nomacro
	lui	$2,%hi(fpStack)
	sll	$4,$4,2
	addiu	$2,$2,%lo(fpStack)
	addu	$4,$2,$4
	lui	$2,%hi(fp)
	lw	$2,%lo(fp)($2)
	sltu	$4,$4,$2
	beq	$4,$0,$L8
	nop

	j	abortStacks
	nop

$L8:
	j	$31
	nop

	.set	macro
	.set	reorder
	.end	checkStack
	.size	checkStack, .-checkStack
	.align	2
	.globl	set
	.set	nomips16
	.set	nomicromips
	.ent	set
	.type	set, @function
set:
	.frame	$sp,32,$31		# vars= 0, regs= 1/0, args= 16, gp= 8
	.mask	0x80000000,-4
	.fmask	0x00000000,0
	.set	noreorder
	.set	nomacro
	addiu	$sp,$sp,-32
	sw	$31,28($sp)
	jal	checkStack
	li	$4,2			# 0x2

	jal	pop
	nop

	jal	pop
	move	$5,$2

	lw	$31,28($sp)
	sw	$2,0($5)
	j	$31
	addiu	$sp,$sp,32

	.set	macro
	.set	reorder
	.end	set
	.size	set, .-set
	.align	2
	.globl	at
	.set	nomips16
	.set	nomicromips
	.ent	at
	.type	at, @function
at:
	.frame	$sp,32,$31		# vars= 0, regs= 1/0, args= 16, gp= 8
	.mask	0x80000000,-4
	.fmask	0x00000000,0
	.set	noreorder
	.set	nomacro
	addiu	$sp,$sp,-32
	sw	$31,28($sp)
	jal	checkStack
	li	$4,1			# 0x1

	jal	pop
	nop

	lw	$31,28($sp)
	lw	$4,0($2)
	j	push
	addiu	$sp,$sp,32

	.set	macro
	.set	reorder
	.end	at
	.size	at, .-at
	.section	.text.startup,"ax",@progbits
	.align	2
	.globl	main
	.set	nomips16
	.set	nomicromips
	.ent	main
	.type	main, @function
main:
	.frame	$sp,0,$31		# vars= 0, regs= 0/0, args= 0, gp= 0
	.mask	0x00000000,0
	.fmask	0x00000000,0
	.set	noreorder
	.set	nomacro
	j	$31
	move	$2,$0

	.set	macro
	.set	reorder
	.end	main
	.size	main, .-main
	.text
	.align	2
	.globl	addEntry
	.set	nomips16
	.set	nomicromips
	.ent	addEntry
	.type	addEntry, @function
addEntry:
	.frame	$sp,0,$31		# vars= 0, regs= 0/0, args= 0, gp= 0
	.mask	0x00000000,0
	.fmask	0x00000000,0
	.set	noreorder
	.set	nomacro
	lui	$2,%hi(dict)
	li	$3,44			# 0x2c
	li	$9,32			# 0x20
	lw	$7,%lo(dict)($2)
	lui	$2,%hi(indexHelp)
	lw	$2,%lo(indexHelp)($2)
	mul	$2,$3,$2
	move	$3,$0
	addu	$8,$7,$2
	sb	$4,0($8)
$L15:
	addu	$4,$5,$3
	lb	$4,0($4)
	beql	$4,$0,$L20
	addiu	$2,$2,44

	addu	$10,$8,$3
	addiu	$3,$3,1
	bne	$3,$9,$L15
	sb	$4,1($10)

	addiu	$2,$2,44
$L20:
	sw	$6,40($8)
	addu	$2,$7,$2
	j	$31
	sw	$2,36($8)

	.set	macro
	.set	reorder
	.end	addEntry
	.size	addEntry, .-addEntry
	.section	.rodata.str1.4,"aMS",@progbits,1
	.align	2
$LC0:
	.ascii	"FORTH\000"
	.align	2
$LC1:
	.ascii	"!\000"
	.align	2
$LC2:
	.ascii	"@\000"
	.text
	.align	2
	.globl	loadDictionary
	.set	nomips16
	.set	nomicromips
	.ent	loadDictionary
	.type	loadDictionary, @function
loadDictionary:
	.frame	$sp,32,$31		# vars= 0, regs= 1/0, args= 16, gp= 8
	.mask	0x80000000,-4
	.fmask	0x00000000,0
	.set	noreorder
	.set	nomacro
	lui	$5,%hi($LC0)
	lui	$6,%hi(forth)
	addiu	$sp,$sp,-32
	li	$4,5			# 0x5
	addiu	$5,$5,%lo($LC0)
	sw	$31,28($sp)
	jal	addEntry
	addiu	$6,$6,%lo(forth)

	lui	$5,%hi($LC1)
	lui	$6,%hi(set)
	li	$4,1			# 0x1
	addiu	$5,$5,%lo($LC1)
	jal	addEntry
	addiu	$6,$6,%lo(set)

	lui	$5,%hi($LC2)
	lui	$6,%hi(at)
	lw	$31,28($sp)
	li	$4,1			# 0x1
	addiu	$5,$5,%lo($LC2)
	addiu	$6,$6,%lo(at)
	j	addEntry
	addiu	$sp,$sp,32

	.set	macro
	.set	reorder
	.end	loadDictionary
	.size	loadDictionary, .-loadDictionary

	.comm	dict,4,4
	.globl	indexHelp
	.section	.bss,"aw",@nobits
	.align	2
	.type	indexHelp, @object
	.size	indexHelp, 4
indexHelp:
	.space	4
	.globl	S0
	.data
	.align	2
	.type	S0, @object
	.size	S0, 4
S0:
	.word	spStack
	.globl	R0
	.align	2
	.type	R0, @object
	.size	R0, 4
R0:
	.word	fpStack

	.comm	word_buf,80,4

	.comm	output,128,4

	.comm	temp,128,4
	.globl	mgs_unknow
	.align	2
	.type	mgs_unknow, @object
	.size	mgs_unknow, 19
mgs_unknow:
	.ascii	"\000x14Unknow Token: \000"
	.globl	sp
	.align	2
	.type	sp, @object
	.size	sp, 4
sp:
	.word	spStack
	.globl	fp
	.align	2
	.type	fp, @object
	.size	fp, 4
fp:
	.word	fpStack

	.comm	spStack,512,4

	.comm	fpStack,512,4
	.ident	"GCC: (Sourcery CodeBench Lite 2014.11-22) 4.9.1"
