<#
.SYNOPSIS
    Scaffolds a new LeetCode problem folder.

.EXAMPLE
    .\new-problem.ps1 -Number 217 -Title "Contains Duplicate" -Difficulty Easy -Topics "Array, Hash Table"
#>
param(
    [Parameter(Mandatory = $true)][int]$Number,
    [Parameter(Mandatory = $true)][string]$Title,
    [ValidateSet('Easy', 'Medium', 'Hard')][string]$Difficulty = 'Easy',
    [string]$Topics = ''
)

$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot

# "Contains Duplicate" -> "contains-duplicate"
$slug = ($Title.ToLower() -replace "[^a-z0-9]+", "-").Trim('-')
$padded = '{0:D4}' -f $Number
$folder = Join-Path $root "$padded-$slug"

if (Test-Path $folder) {
    Write-Host "Already exists: $padded-$slug" -ForegroundColor Yellow
    return
}

New-Item -ItemType Directory -Path $folder | Out-Null

# UTF-8 without BOM. PowerShell 5.1's -Encoding utf8 writes a BOM, which javac
# can reject, and Get-Content defaults to ANSI on read - that combination
# corrupts any non-ASCII character it round-trips.
$utf8 = New-Object System.Text.UTF8Encoding($false)

$solution = @"
class Solution {
    // TODO: paste your accepted solution here
}
"@
[System.IO.File]::WriteAllText((Join-Path $folder 'Solution.java'), $solution, $utf8)

$problemReadme = @"
# $Number. $Title

**Difficulty:** $Difficulty
**Topics:** $Topics
**Link:** https://leetcode.com/problems/$slug/

## Problem

TODO

## Approach

TODO

## Complexity

- **Time:** O(?)
- **Space:** O(?)
"@
[System.IO.File]::WriteAllText((Join-Path $folder 'README.md'), $problemReadme, $utf8)

# Add a row to the progress table in the root README
$readme = Join-Path $root 'README.md'
$topicText = if ($Topics) { $Topics } else { '-' }
$row = "| $Number | [$Title](https://leetcode.com/problems/$slug/) | $Difficulty | $topicText | [Java](./$padded-$slug/Solution.java) |"

$lines = [System.Collections.Generic.List[string]][System.IO.File]::ReadAllLines($readme, [System.Text.Encoding]::UTF8)
$rows = $lines | Select-String -Pattern '^\| (\d+) \|'
if ($rows) {
    # Insert in problem-number order, not at the end - solving 13 after 242
    # should still place it between 9 and 217.
    $insertAt = $null
    foreach ($r in $rows) {
        if ([int]$r.Matches[0].Groups[1].Value -gt $Number) {
            $insertAt = $r.LineNumber - 1
            break
        }
    }
    if ($null -eq $insertAt) { $insertAt = ($rows | Select-Object -Last 1).LineNumber }
    $lines.Insert($insertAt, $row)

    # Bump the solved counter to match the number of table rows
    $count = ($lines | Select-String -Pattern '^\| \d+ \|').Count
    for ($i = 0; $i -lt $lines.Count; $i++) {
        if ($lines[$i] -match '^\*\*Total solved:\*\*') {
            $lines[$i] = "**Total solved:** $count"
            break
        }
    }
    [System.IO.File]::WriteAllLines($readme, $lines, $utf8)
} else {
    Write-Host "Couldn't find the progress table - add the row manually:" -ForegroundColor Yellow
    Write-Host $row
}

Write-Host "Created $padded-$slug" -ForegroundColor Green
Write-Host "  -> $folder\Solution.java"
