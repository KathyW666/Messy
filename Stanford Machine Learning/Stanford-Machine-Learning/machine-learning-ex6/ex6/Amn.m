function [result] = Amn(set)

result = [];
for i = 1:length(set)
    result = vertcat(result, [ones(length(set),1)*set(i) set']);
end