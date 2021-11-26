import unittest

ops = {
    '*': {'precedence': 2,'op': lambda x,y: x * y},
    '/': {'precedence': 2,'op': lambda x,y: x / y},
    '+': {'precedence': 1,'op': lambda x,y: x + y},
    '-': {'precedence': 1,'op': lambda x,y: x -  y},
    '(': {'precedence': 0},
}

LH = '(' 
RH = ')'

def evaluate(expression, debug=False, result_as_integer = True):
    if debug:
        print('POSTFIX: %s' % ' '.join(expression))
    stack = []
    for token in expression:
        if token in ops:
            arg2 = stack.pop()
            arg1 = stack.pop()
            result = ops[token]['op'](arg1, arg2)
            stack.append(result)
        else:
            stack.append(int(token))
    if result_as_integer:
        return int(stack.pop())
    return stack.pop()

def algo(expression, debug = False):
    
    out_stack = []
    ops_stack = []
    
    if debug:
        print('INFIX:', expression)
    
    # Check matching parenthesis
    lhs = sum(c == '(' for c in expression)
    rhs = sum(c == ')' for c in expression)
    if lhs != rhs:
        raise Exception('Unable to parse INFIX due to unmatched parenthesis')
    
    # While there are expression tokens to be read
    for token in expression.split(' '):
        
        # If it's a number add it to the output stack
        if token.lstrip("-").isdigit():
            out_stack.append(token)
            
        # If it's a left bracket
        elif token == LH:
            # Push it onto the operator stack
            ops_stack.append(token)
            
        # If it's a right bracket
        elif token == RH:
            # While there's not a left bracket at the top of the stack:
            while len(ops_stack) > 0 and ops_stack[-1] != LH:
                # Pop operators from the operator stack onto the output stack
                out_stack.append(ops_stack.pop())
            # Pop the left bracket from the operator stack and discard it
            ops_stack.pop()
            
        # If it's an operator
        elif token in ops:
            # While there's an operator on the top of the operator stack with greater precedence
            while len(ops_stack) > 0 and ops[ops_stack[-1]]['precedence'] >= ops[token]['precedence']:
                # Pop operators from the operator stack onto the output stack
                out_stack.append(ops_stack.pop())
            # Push the current operator onto the stack
            ops_stack.append(token)
            
    # While there are operators on the operator stack, pop them to the output stack       
    while ops_stack:
        out_stack.append(ops_stack.pop())
    return out_stack
