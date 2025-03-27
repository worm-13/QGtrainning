#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// 定义计算器
typedef struct Reckoner {
    union {
        int num;    // 存储操作数
        char op;    // 存储操作符
    } data;
    int isOperator; // 标记是操作数还是操作符（1表示操作符，0表示操作数）
    struct Reckoner *next;
} Reckoner, *top;

// 初始化
void InitStack(top *S) {
    *S = NULL;
}

// 判断栈是否为空
int StackEmpty(top S) {
    return S == NULL;
}

// 入栈
void Push(top *S, int isOperator, int num, char op) {
    Reckoner *newNode = (Reckoner *)malloc(sizeof(Reckoner));
    newNode->isOperator = isOperator;
    if (isOperator) {
        newNode->data.op = op;
    } else {
        newNode->data.num = num;
    }
    newNode->next = *S;
    *S = newNode;
}

// 数字出栈
int PopNum(top *S) {
    if (StackEmpty(*S)) {
        printf("Stack underflow\n");
        exit(1);
    }
    Reckoner *temp = *S;
    int num = temp->data.num;
    *S = temp->next;
    free(temp);
    return num;
}

// 运算符出栈
char PopOp(top *S) {
    if (StackEmpty(*S)) {
        printf("Stack underflow\n");
        exit(1);
    }
    Reckoner *temp = *S;
    char op = temp->data.op;
    *S = temp->next;
    free(temp);
    return op;
}

// 获取栈顶元素，数字或运算符
char GetTopOp(top S) {
    if (StackEmpty(S)) {
        printf("Stack is empty\n");
        exit(1);
    }
    return S->data.op;
}

// 判断优先级
int Priority(char op) {
    switch (op) {
        case '+':
        case '-':
            return 1;
        case '*':
        case '/':
            return 2;
        default:
            return 0;
    }
}

// 定义四则运算
int Calculate(int a, int b, char op) {
    switch (op) {
        case '+': return a + b;
        case '-': return a - b;
        case '*': return a * b;
        case '/':
            if (b == 0) {
                printf("Error: Division by zero\n");
                exit(1);
            }
            return a / b;
        default:
            return 0;
    }
}

// 难中难，转为后缀表达式
void InfixToPostfix(char *infix, char *postfix) {
    top S;
    InitStack(&S);
    int i = 0, j = 0;

    while (infix[i] != '\0') {
        if (isdigit(infix[i])) {
            while (isdigit(infix[i])) {
                postfix[j++] = infix[i++];
            }
            postfix[j++] = ' ';
            i--;
        } else if (infix[i] == '(') {
            Push(&S, 1, 0, infix[i]);
        } else if (infix[i] == ')') {
            while (!StackEmpty(S) && GetTopOp(S) != '(') {
                postfix[j++] = PopOp(&S);
                postfix[j++] = ' ';
            }
            if (!StackEmpty(S) && GetTopOp(S) == '(') {
                PopOp(&S);
            } else {
                printf("Error: Mismatched parentheses\n");
                exit(1);
            }
        } else if (infix[i] == '+' || infix[i] == '-' || infix[i] == '*' || infix[i] == '/') {
            while (!StackEmpty(S) && Priority(GetTopOp(S)) >= Priority(infix[i])) {
                postfix[j++] = PopOp(&S);
                postfix[j++] = ' ';
            }
            Push(&S, 1, 0, infix[i]);
        }
        i++;
    }

    while (!StackEmpty(S)) {
        if (GetTopOp(S) == '(') {
            printf("Error: Mismatched parentheses\n");
            exit(1);
        }
        postfix[j++] = PopOp(&S);
        postfix[j++] = ' ';
    }
    postfix[j] = '\0';
}

// 算后缀表达式
int EvaluatePostfix(char *postfix) {
    top S;
    InitStack(&S);
    int i = 0;

    while (postfix[i] != '\0') {
        if (isdigit(postfix[i])) {
            int num = 0;
            while (isdigit(postfix[i])) {
                num = num * 10 + (postfix[i] - '0');
                i++;
            }
            Push(&S, 0, num, '\0');
        } else if (postfix[i] == ' ') {
            i++;
        } else {
            int b = PopNum(&S);
            int a = PopNum(&S);
            int result = Calculate(a, b, postfix[i]);
            Push(&S, 0, result, '\0');
            i++;
        }
    }

    return PopNum(&S);
}

int main() {
    char infix[100];
    char postfix[100];
    
    printf("请输入计算式：");
    fgets(infix, sizeof(infix), stdin);
    infix[strcspn(infix, "\n")] = '\0';// 最坑壁的，fgets进去的字符串会带回车，所以要删掉回车，本来想用getchar吃掉回车，但是不知道为什么输入完就要按两次回车

    InfixToPostfix(infix, postfix);
    printf("后缀表达式: %s\n", postfix);
    
    int result = EvaluatePostfix(postfix);
    printf("计算结果: %d\n", result);
    
    return 0;
}